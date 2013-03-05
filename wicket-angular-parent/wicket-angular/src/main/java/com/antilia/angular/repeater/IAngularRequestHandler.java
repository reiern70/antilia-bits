package com.antilia.angular.repeater;

import java.io.Serializable;
import java.util.Map;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.http.WebResponse;

/**
 * 
 * @author reiern70
 *
 */
public interface IAngularRequestHandler extends Serializable {
	
	/**
	 * Returns true if it can handle request.
	 * @param request
	 * @return
	 */
	public boolean canHandleRequest(Request request);
	
	/**
	 * Handles the request.
	 * 
	 * @param request
	 * @param response
	 */
	public void handleRequest(Request request, WebResponse response);
	
	
	/**
	 * Allows handler to contribute to scope.
	 * 
	 * @param variables
	 * @return
	 */
	public String contributeToScope(Map<String, Object> variables);
}
