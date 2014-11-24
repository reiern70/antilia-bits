package com.antilia.task;

import java.io.Serializable;

/**
 * @author reiern70
 *
 */
public interface ITask extends Serializable {

	void doIt(ExecutionBridge bridge);
}
