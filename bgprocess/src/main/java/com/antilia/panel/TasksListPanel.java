package com.antilia.panel;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

import com.antilia.BgProcessSession;
import com.antilia.task.ExecutionBridge;

/**
 * @author reiern70
 */
@SuppressWarnings("serial")
public class TasksListPanel extends Panel {
	/**
	 * @param id
	 */
	public TasksListPanel(String id) {
		super(id);
		setOutputMarkupId(true);
		add( new AjaxLink<Void>("prune") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				BgProcessSession.getSession().pruneFinishedTasks();
				target.add(TasksListPanel.this);
			}
		});
		add( new AjaxSelfUpdatingTimerBehavior( Duration.seconds(5) ) );
		ArrayList<IColumn<ExecutionBridge, String>> columns = new ArrayList<IColumn<ExecutionBridge,String>>();
		columns.add( new PropertyColumn<ExecutionBridge, String>(Model.of("Actions"), "-") { 
			@Override
			public void populateItem(
					Item<ICellPopulator<ExecutionBridge>> item,
					String componentId, IModel<ExecutionBridge> rowModel) {
				item.add( new ActionsPanel(componentId, rowModel) {					
					@Override
					public void onAction(AjaxRequestTarget target) {
						TasksListPanel.this.add(new AjaxSelfUpdatingTimerBehavior( Duration.seconds(5)));
						target.add(TasksListPanel.this);
					}
				});
			}
		});
		columns.add( new PropertyColumn<ExecutionBridge, String>(Model.of("Name"), "taskName") );
		columns.add( new PropertyColumn<ExecutionBridge, String>(Model.of("Stoped"), "stop") );
		columns.add( new PropertyColumn<ExecutionBridge, String>(Model.of("Canceled"), "cancel") );
		columns.add( new PropertyColumn<ExecutionBridge, String>(Model.of("Message"), "message") );
		columns.add( new PropertyColumn<ExecutionBridge, String>(Model.of("Progress"), "progress") );
		columns.add( new PropertyColumn<ExecutionBridge, String>(Model.of("Finished"), "finished") );
		AjaxFallbackDefaultDataTable<ExecutionBridge, String> tasks = new AjaxFallbackDefaultDataTable<>("tasks", columns, new TaskDataProvider(), 10);
		add(tasks);
	}
	
	@Override
	public void onEvent(IEvent<?> event) {
		if(event.getPayload() instanceof TaskLaunchedEvent) {
			TaskLaunchedEvent event2 = (TaskLaunchedEvent)event.getPayload();
			TasksListPanel.this.add(new AjaxSelfUpdatingTimerBehavior( Duration.seconds(5)));
			event2.getTarget().add(TasksListPanel.this);
		}
	}
}
