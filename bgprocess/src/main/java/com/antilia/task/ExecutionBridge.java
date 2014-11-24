package com.antilia.task;

import java.io.Serializable;

/**
 * Bridge, honoring its name, serve as a context to establish communication between
 * WEB layer and Service (Back-ground threads layer).
 * 
 * @author reiern70
 */
@SuppressWarnings("serial")
public class ExecutionBridge implements Serializable {

	private String taskName;

	private volatile boolean stop = false;

	private volatile boolean cancel = false;

	private volatile int progress = 0;

	private String message;

	public ExecutionBridge() {
	}


	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public boolean isFinished() {
		return isCancel() || progress >= 100;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
