/**
 * 
 */
package com.antilia.service.impl;

import com.antilia.service.IAnswerService;
import com.antilia.task.dummy.DummyTask;
import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author reiern70
 *
 */
public class AnswerService implements IAnswerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnswerService.class);

	public AnswerService() {
	}

	/* (non-Javadoc)
	 * @see com.antilia.service.IAnswerService#getMessage(com.antilia.service.IAnswerService.AnswerType, int)
	 */
	@Override
	public String getMessage(AnswerType type, int i) {
		LOGGER.info("App: {}", Application.exists());
		LOGGER.info("Session: {}", Session.exists());


		if(AnswerType.IS_CANCEL.equals(type)) {
			return "Taking the poison pill at " + i + "!";
		}
		if(AnswerType.ALL_OK.equals(type)) {
			return "Doing nothing for the " + i + "th time!";
		}
		if(AnswerType.IS_STOP.equals(type)) {
			return "Having a little nap at " + i + "!";
		}
		if(AnswerType.IS_ERROR.equals(type)) {
			return "Oops... Something went wrong! ";
		}
		return "Know nothing about this!";
	}
}
