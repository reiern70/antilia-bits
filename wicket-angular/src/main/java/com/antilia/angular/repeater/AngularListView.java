package com.antilia.angular.repeater;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;

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

	private final String viewName;
	
	private String mountPath;

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param elements
	 * @param ijsoNifier
	 */
	public AngularListView(String id, List<B> elements, IJSONifier<B> ijsoNifier) {
		super(id);
		this.elements = elements;
		this.ijsoNifier = ijsoNifier;
		this.setOutputMarkupId(true);
		this.viewName = "AngularRepeatingView" + getMarkupId();
		this.add(new AttributeModifier("ng-controller", Model.of(this.viewName)));
	}
	
	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param mountPath
	 */
	public AngularListView(String id, String mountPath) {
		super(id);
		this.mountPath = mountPath;
		this.setOutputMarkupId(true);
		this.viewName = "AngularRepeatingView" + getMarkupId();
		this.add(new AttributeModifier("ng-controller", Model.of(this.viewName)));
	}

	@Override
	public void onResourceRequested() {
		if(getMountPath() == null) {
			WebResponse webResponse = (WebResponse) getRequestCycle().getResponse();
			webResponse.setContentType("application/json");
			generateJSON(ijsoNifier, elements.iterator(), webResponse.getOutputStream());
		}
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		long size = elements != null? elements.size(): -1;
		StringBuilder builder = new StringBuilder();
		builder.append("function ");
		builder.append(viewName);
		builder.append("($scope, $http) {\n");
		builder.append("$scope.size=");
		builder.append(size);
		builder.append(";\n");
		// if there is a mount path use it.
		CharSequence url = getMountPath() != null? getMountPath(): urlFor(IResourceListener.INTERFACE, null);
		builder.append("$http.get('");
		builder.append(url);
		builder.append("').success(function(data) {\n");
		builder.append("$scope.elements  = data;\n");
		builder.append("});");
		contributeToScope(builder);
			builder.append("}\n");
		response.render(JavaScriptHeaderItem.forScript(builder.toString(), null));
	}
	
	/**
	 * Overrride to contribute to scope;
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

	public String getViewName() {
		return viewName;
	}

	public String getMountPath() {
		return mountPath;
	}

	public void setMountPath(String mountPath) {
		this.mountPath = mountPath;
	}

}
