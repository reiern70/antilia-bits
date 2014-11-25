/**
 * 
 */
package com.antilia.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.panel.Panel;

import com.antilia.BgProcessApplication;
import com.antilia.task.dummy.DummyTask;

/**
 * @author reiern70
 *
 */
@SuppressWarnings("serial")
public class LaunchDummyTaskPanel extends Panel {

	/**
	 * @param id
	 */
	public LaunchDummyTaskPanel(String id) {
		super(id);
		add( new AjaxLink<Void>("launch") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				BgProcessApplication.getApplication().launch( new DummyTask() );
				getPage().send(getPage(), Broadcast.DEPTH, new TaskLaunchedEvent(target));
			}
		});
	}
}
