package com.antilia.angular.repeater;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.template.PackageTextTemplate;

/**
 * Angular powered ListView.
 * 
 * @author Ernesto Reinaldo Barreiro (reiern70).
 * 
 * <B> The data bean of the List View
 */
public class AngularListView<B> extends Panel implements IResourceListener {

	private static final long serialVersionUID = 1L;

	private IJSONifier<B> ijsoNifier;

	private List<B> elements;

	private final String scope;
	
	private String mountPath;
		
	
	private List<IAngularRequestHandler> handlers = new ArrayList<IAngularRequestHandler>();
	
	private static abstract class JSONListHandler<B> implements IAngularRequestHandler {

		private static final long serialVersionUID = 1L;
				
		private boolean lazy;
		
		private static final String JSON_DATA = "_jonsData";
		
		/**
		 * Constructor.
		 * 
		 * @param lazy
		 */
		public JSONListHandler(boolean lazy) {
			this.lazy = lazy;
		}
		
		@Override
		public String contributeToScope(Map<String, Object> variables) {
			StringBuilder builder = new StringBuilder();
			String url = (String)variables.get("url");
			url += url.indexOf('?')>0?("&"+JSON_DATA+"=true"):"";
			if(lazy) {
				builder.append("$http.get('");
				builder.append(url);
				builder.append("').success(function(data) {");
				builder.append("$scope.elements  = data;");
				builder.append("});");		
				return builder.toString();
			} else {
				  ByteArrayOutputStream webResponse = new ByteArrayOutputStream();
				  generateJSON(getAngularListView().ijsoNifier, getAngularListView().elements.iterator(), webResponse);
				
				  builder.append("$scope.elements  = ");
				  try {
					builder.append(webResponse.toString(RequestCycle.get().getRequest().getCharset().name()));
				  } catch (UnsupportedEncodingException e) {
					  throw new WicketRuntimeException(e);
				  }
				  return builder.toString();
			}
		}

		@Override
		public boolean canHandleRequest(Request request) {
			return getAngularListView().getMountPath() == null && request.getRequestParameters().getParameterValue(JSON_DATA).toString() != null;
		}

		@Override
		public void handleRequest(Request request, WebResponse response) {
			response.setContentType("application/json");
			generateJSON(getAngularListView().ijsoNifier, getAngularListView().elements.iterator(), response.getOutputStream());
		}
		
		protected abstract AngularListView<B> getAngularListView();
		
	}
	
	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param elements
	 * @param ijsoNifier
	 * @param scope;
	 * @param lazy;
	 */
	public AngularListView(String id, List<B> elements, IJSONifier<B> ijsoNifier, String scope, boolean lazy) {
		super(id);
		this.elements = elements;
		this.ijsoNifier = ijsoNifier;
		this.setOutputMarkupId(true);
		this.scope = !Strings.isEmpty(scope)?scope:("AngularListView" + getMarkupId());
		handlers.add(new JSONListHandler<B>(lazy) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected AngularListView<B> getAngularListView() {
				return AngularListView.this;
			}
		});
		this.add(new AttributeModifier("ng-controller", Model.of(this.getScope())));
	}
	
	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param mountPath
	 * @param scope
	 */
	public AngularListView(String id, String mountPath, String scope) {
		super(id);
		this.mountPath = mountPath;
		this.setOutputMarkupId(true);
		this.scope = !Strings.isEmpty(scope)?scope:"AngularListView" + getMarkupId();
		handlers.add(new JSONListHandler<B>(true) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected AngularListView<B> getAngularListView() {
				return AngularListView.this;
			}
		});
		this.add(new AttributeModifier("ng-controller", Model.of(this.getScope())));
	}

	@Override
	public void onResourceRequested() {
		 WebResponse response = (WebResponse)RequestCycle.get().getResponse();
		 Request request = RequestCycle.get().getRequest();
		for(IAngularRequestHandler handler: handlers) {
			if(handler.canHandleRequest(request)) {
				handler.handleRequest(request, response);
				break;
			}
		}
	}

	/**
	 * 
	 * @param handler
	 */
	public void addHandler(IAngularRequestHandler handler) {
		handlers.add(handler);
	}
	
	public void removeHandler(IAngularRequestHandler handler) {
		handlers.remove(handler);
	}
	
	@SuppressWarnings("resource")
	@Override
	public void renderHead(IHeaderResponse response) {
		CharSequence url = getMountPath() != null? getMountPath(): urlFor(IResourceListener.INTERFACE, null);			
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("url", url);
		variables.put("scope", scope);		
		variables.put("additionalParams", getAdditionalParameters());
		StringBuilder extraContributions = new StringBuilder();
		for(IAngularRequestHandler handler: handlers) {
			String string = handler.contributeToScope(variables);
			if(string != null) {
				extraContributions.append(string.trim());
				extraContributions.append(";");
			}
		}
		
		contributeToScope(extraContributions);
		
		variables.put("extraContributions", !Strings.isEmpty(extraContributions)?extraContributions.toString():"");
		PackageTextTemplate scriptTemplate = new PackageTextTemplate(AngularListView.class, "angularListView.js");
		response.render(JavaScriptHeaderItem.forScript(scriptTemplate.asString(variables), getMarkupId()));
	}
	
	/**
	 * @return Return the additional parameter for controller (to be injected by angular).
	 */
	protected String getAdditionalParameters() {
		return "";
	}
	
	/**
	 * Override to contribute to scope;
	 * @param scope
	 */
	protected void contributeToScope(StringBuilder scope) {
		
	}

	public static <T> void generateJSON(IJSONifier<T> ijsoNifier,
			Iterator<? extends T> it, OutputStream outputStream) {
		Request request = RequestCycle.get().getRequest();
		//IRequestParameters params = request.getRequestParameters();

		OutputStreamWriter out = new OutputStreamWriter(outputStream, request.getCharset());
		try {
			out.write("[");
			while (it.hasNext()) {
				out.write(ijsoNifier.toJSON(it.next()));
				if(it.hasNext()) {
					out.write(",");
				}
			}
			out.write("]");
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException(
					"Could not write JSON to servlet response", e);
		}
	}

	public String getScope() {
		return scope;
	}

	public String getMountPath() {
		return mountPath;
	}

	public void setMountPath(String mountPath) {
		this.mountPath = mountPath;
	}

	public IJSONifier<B> getIjsoNifier() {
		return ijsoNifier;
	}

	public void setIjsoNifier(IJSONifier<B> ijsoNifier) {
		this.ijsoNifier = ijsoNifier;
	}

}
