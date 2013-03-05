/**
 * 
 */
package com.antilia.angular.repeater;

import java.util.Map;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.http.WebResponse;

/**
 * 
 * IAngularRequestHandler used to catch an event and broadcast a new one.
 * 
 * @author reiern70
 *	
 */
public class EventBroadcasterHandler implements IAngularRequestHandler {

	private static final long serialVersionUID = 1L;
		
	private String inEvent;
	
	private String outEvent;
	
	/**
	 * Constructor.
	 * 
	 * @param inEvent
	 * @param outEvent
	 */
	public EventBroadcasterHandler(String inEvent, String outEvent) {
		this.inEvent = inEvent;
		this.outEvent = outEvent;
	}

	/* (non-Javadoc)
	 * @see com.antilia.angular.repeater.IAngularRequestHandler#canHandleRequest(org.apache.wicket.request.Request)
	 */
	@Override
	public boolean canHandleRequest(Request request) {
		return false;		
	}

	/* (non-Javadoc)
	 * @see com.antilia.angular.repeater.IAngularRequestHandler#handleRequest(org.apache.wicket.request.Request, org.apache.wicket.request.http.WebResponse)
	 */
	@Override
	public void handleRequest(Request request, WebResponse response) {
	
	}
	
	
	/* (non-Javadoc)
	 * @see com.antilia.angular.repeater.IAngularRequestHandler#contributeToScope(java.util.Map)
	 */
	@Override
	public String contributeToScope(Map<String, Object> variables) {
		StringBuilder builder = new StringBuilder();
		builder.append("$scope.$on('");
		builder.append(inEvent);
		builder.append("', function(event, args) {\n");
		builder.append("$scope.$broadcast('");
		builder.append(outEvent);
		builder.append("', args)\n");
		builder.append("});");
		return builder.toString();

	}

	public String getOutEvent() {
		return outEvent;
	}

	public void setOutEvent(String outEvent) {
		this.outEvent = outEvent;
	}

}
