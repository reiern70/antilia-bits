package com.antilia.task;


/**
 * @author reiern70
 *
 */
public class TasksRunnable implements Runnable {
	
	private ITask task;
	private ExecutionBridge bridge;	
	
	public TasksRunnable(ITask task, ExecutionBridge bridge) {
		this.task = task;
		this.bridge = bridge;
	}
	
	@Override
	public void run() {
		task.doIt(bridge);
	}

}
