/**
 * 
 */
package com.antilia.angular.repeater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.template.PackageTextTemplate;

/**
 * @author reiern70
 *
 */
public class AngularWebMarkupContainer extends WebMarkupContainer implements IResourceListener {

	private static final long serialVersionUID = 1L;

	private String scope;
	
	private List<IAngularRequestHandler> handlers = new ArrayList<IAngularRequestHandler>();
	
	/**
	 * @param id
	 */
	public AngularWebMarkupContainer(String id, String scope) {
		super(id);
		this.scope = !Strings.isEmpty(scope)? scope: "AngularWebMarkupContainer"+getMarkupId();
		this.add(new AttributeModifier("ng-controller", Model.of(this.getScope())));
	}

	/**
	 * @param id
	 * @param model
	 */
	public AngularWebMarkupContainer(String id, IModel<?> model, String scope) {
		super(id, model);
		this.scope = !Strings.isEmpty(scope)? scope: "AngularWebMarkupContainer"+getMarkupId();
		this.add(new AttributeModifier("ng-controller", Model.of(this.getScope())));
	}
	
	@SuppressWarnings("resource")
	@Override
	public void renderHead(IHeaderResponse response) {
		CharSequence url = urlFor(IResourceListener.INTERFACE, null);			
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
		PackageTextTemplate scriptTemplate = new PackageTextTemplate(AngularListView.class, "angularPanel.js");
		response.render(JavaScriptHeaderItem.forScript(scriptTemplate.asString(variables), getMarkupId()));
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
	
	/**
	 * Override to contribute to scope;
	 * @param scope
	 */
	protected void contributeToScope(StringBuilder scope) {
		
	}
	
	/**
	 * @return Return the additional parameter for controller (to be injected by angular).
	 */
	protected String getAdditionalParameters() {
		return "";
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.IResourceListener#onResourceRequested()
	 */
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

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
