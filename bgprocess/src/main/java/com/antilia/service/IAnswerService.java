/**
 * 
 */
package com.antilia.service;

/**
 * @author reiern70
 *
 */
public interface IAnswerService {
	
	public static enum AnswerType {
		ALL_OK,
		IS_STOP,
		IS_CANCEL,
		IS_ERROR,
	}

	String getMessage(AnswerType type, int i);
}
