/**
 * 
 */
package com.antilia.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import com.antilia.task.ExecutionBridge;

/**
 * 
 * @author reiern70
 */
@SuppressWarnings("serial")
public abstract class ActionsPanel extends GenericPanel<ExecutionBridge> {

	/**
	 * @param id
	 */
	public ActionsPanel(String id, IModel<ExecutionBridge> model) {
		super(id, model);
		add( new AjaxLink<String>("stop") {	
			@Override
			public void onClick(AjaxRequestTarget target) {
				ActionsPanel.this.getModel().getObject().setStop(!ActionsPanel.this.getModel().getObject().isStop());
				onAction(target);
			}			
			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisible(!ActionsPanel.this.getModel().getObject().isFinished());
			}
		}.setBody(new AbstractReadOnlyModel<String>(){ 
			@Override
			public String getObject() {
				return ActionsPanel.this.getModel().getObject().isStop()?"Restart":"Stop/Pause";
			}
		}));		
		add( new AjaxLink<Void>("cancel") {
			
			@Override
			public void onClick(AjaxRequestTarget target) {
				ActionsPanel.this.getModel().getObject().setCancel(true);
				onAction(target);
			}
			
			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisible(!ActionsPanel.this.getModel().getObject().isFinished());
			}
		});
	}
	
	public abstract void onAction(AjaxRequestTarget target);
}
