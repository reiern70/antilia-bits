/**
 * 
 */
package com.antilia.service.impl;

import com.antilia.service.IAnswerService;

/**
 * @author reiern70
 *
 */
public class AnswerService implements IAnswerService {
	
	public AnswerService() {
	}

	/* (non-Javadoc)
	 * @see com.antilia.service.IAnswerService#getMessage(com.antilia.service.IAnswerService.AnswerType, int)
	 */
	@Override
	public String getMessage(AnswerType type, int i) {
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
