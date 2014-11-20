package com.antilia.task.dummy;

import com.antilia.task.ExecutionBridge;
import com.antilia.task.ITask;

@SuppressWarnings("serial")
public class DummyTask implements ITask {

	@Override
	public void doIt(ExecutionBridge bridge) {
		for(int i =1; i <= 100; ) {
			bridge.setProgress(i);	
			if(bridge.isCancel()) {
				bridge.setMessage("Taking the poison pill at " + i + "!");
				// end the task!
				return;
			}
			if(bridge.isStop()) {
				bridge.setMessage("Stopped at " + i + "!");
			} else {
				bridge.setMessage("Doing nothing for the " + i + "th time!");
			}
			i++;
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				bridge.setProgress(100);
				bridge.setMessage("Oops... Something went wrong! " + e.getMessage());
				return;
			}		
		}
	}
}
