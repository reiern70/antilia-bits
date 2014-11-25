package com.antilia.task.dummy;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;

import com.antilia.service.IAnswerService;
import com.antilia.service.IAnswerService.AnswerType;
import com.antilia.task.ExecutionBridge;
import com.antilia.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class DummyTask implements ITask {

	private static final Logger LOGGER = LoggerFactory.getLogger(DummyTask.class);

	@javax.inject.Inject
	private IAnswerService answerService;
	
	public DummyTask() {
		Injector.get().inject(this);
	}
	
	@Override
	public void doIt(ExecutionBridge bridge) {

		LOGGER.info("App: {}", Application.exists());
		LOGGER.info("Session: {}", Session.exists());

		for(int i =1; i <= 100; ) {
			bridge.setProgress(i);
			if(bridge.isCancel()) {
				bridge.setMessage(answerService.getMessage(AnswerType.IS_CANCEL, i));
				// end the task!
				return;
			}
			if(bridge.isStop()) {
				bridge.setMessage(answerService.getMessage(AnswerType.IS_STOP, i));
			} else {
				bridge.setMessage(answerService.getMessage(AnswerType.ALL_OK, i));
				i++;
			}	
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				bridge.setProgress(100);
				bridge.setMessage(answerService.getMessage(AnswerType.IS_ERROR, i));
				return;
			}
		}
	}
}
