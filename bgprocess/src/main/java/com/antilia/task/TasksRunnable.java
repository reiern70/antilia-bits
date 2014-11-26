package com.antilia.task;


import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.ThreadContext;

/**
 * @author reiern70
 *
 */
public class TasksRunnable implements Runnable {

	private final ITask task;
	private final ExecutionBridge bridge;
	private final Application application;
	private final Session session;

	public TasksRunnable(ITask task, ExecutionBridge bridge) {
		this.task = task;
		this.bridge = bridge;

		this.application = Application.get();
		this.session = Session.exists() ? Session.get() : null;
	}

	@Override
	public void run() {
		try {
			ThreadContext.setApplication(application);
			ThreadContext.setSession(session);
			task.doIt(bridge);
		} finally {
			ThreadContext.detach();
		}
	}
}
