/**
 * 
 */
package com.antilia.panel;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.antilia.BgProcessSession;
import com.antilia.task.ExecutionBridge;

/**
 * @author reiern70
 *
 */
@SuppressWarnings("serial")
public class TaskDataProvider extends SortableDataProvider<ExecutionBridge, String> {
	
	@Override
	public Iterator<? extends ExecutionBridge> iterator(long start, long size) {
		return BgProcessSession.getSession().getTasksPage((int)start, (int)size);
	}
	
	@Override
	public IModel<ExecutionBridge> model(ExecutionBridge object) {
		return Model.of(object);
	}
	
	public long size() {
		return BgProcessSession.getSession().countTasks();
	}

}
