/**
 * 
 */
package com.antilia.panel;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author reiern70
 *
 */
@SuppressWarnings("serial")
public class TaskLaunchedEvent implements Serializable {

	private AjaxRequestTarget target;
	/**
	 * 
	 */
	public TaskLaunchedEvent(AjaxRequestTarget target) {
		this.target = target;
	}
	public AjaxRequestTarget getTarget() {
		return target;
	}
	public void setTarget(AjaxRequestTarget target) {
		this.target = target;
	}

	
}
