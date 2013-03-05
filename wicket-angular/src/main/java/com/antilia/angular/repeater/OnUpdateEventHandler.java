/**
 * 
 */
package com.antilia.angular.repeater;

import java.util.Map;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.template.PackageTextTemplate;

/**
 * 
 * IAngularRequestHandler used to update 
 * 
 * @author reiern70
 *	
 *	B the class
 */
public abstract class OnUpdateEventHandler<B> implements IAngularRequestHandler {

	private static final long serialVersionUID = 1L;
	
	private static final String elementId = "_elementId";
	
	private String updateEvent;
	/**
	 * 
	 */
	public OnUpdateEventHandler(String updateEvent) {
		this.updateEvent = updateEvent;
	}

	/* (non-Javadoc)
	 * @see com.antilia.angular.repeater.IAngularRequestHandler#canHandleRequest(org.apache.wicket.request.Request)
	 */
	@Override
	public boolean canHandleRequest(Request request) {
		return request.getRequestParameters().getParameterValue(OnUpdateEventHandler.elementId).toString() != null;		
	}

	/* (non-Javadoc)
	 * @see com.antilia.angular.repeater.IAngularRequestHandler#handleRequest(org.apache.wicket.request.Request, org.apache.wicket.request.http.WebResponse)
	 */
	@Override
	public void handleRequest(Request request, WebResponse response) {
		response.setContentType("application/json");
		Long elementId = Long.parseLong(request.getRequestParameters().getParameterValue(OnUpdateEventHandler.elementId).toString());
		B bean = getElement(elementId);
		if(bean != null) {
			response.write(getJSON(bean));
		} else {
			response.write("{\"error\": \"could not find bean with ID: "+elementId+"\"}");
		}
	}
	
	/**
	 * Returns the element given the ID.
	 * 
	 * @param id
	 * @return
	 */
	protected abstract B getElement(Long id);
	
	/**
	 * Returns the JSON representation for the bean.
	 * 
	 * @param bean
	 * @return
	 */
	protected abstract String getJSON(B bean);

	/* (non-Javadoc)
	 * @see com.antilia.angular.repeater.IAngularRequestHandler#contributeToScope(java.util.Map)
	 */
	@Override
	public String contributeToScope(Map<String, Object> variables) {
		variables.put("elementId", elementId);
		variables.put("updateEvent", updateEvent);
		@SuppressWarnings("resource")
		PackageTextTemplate scriptTemplate = new PackageTextTemplate(OnUpdateEventHandler.class, "updatedetails.js");
		return scriptTemplate.asString(variables);
	}

}
