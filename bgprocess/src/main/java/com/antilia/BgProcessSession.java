package com.antilia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import com.antilia.task.ExecutionBridge;

/**
 * Session holding BG processes launched by user.
 * 
 * @author reiern70
 */
@SuppressWarnings("serial")
public class BgProcessSession extends WebSession {

	private List<ExecutionBridge> bridges = new ArrayList<ExecutionBridge>();

	public BgProcessSession(Request request) {
		super(request);
	}

	public void add(ExecutionBridge bridge) {
		bridges.add(bridge);
	}

	public synchronized void  pruneFinishedTasks() {
		ArrayList<ExecutionBridge> nonFinishedBridges = new ArrayList<ExecutionBridge>();
		for(ExecutionBridge bridge: this.bridges) {
			if (!bridge.isFinished()) {
				nonFinishedBridges.add(bridge);
			}
		}
		this.bridges = nonFinishedBridges;
	}

	public Iterator<ExecutionBridge> getTasksPage(int start, int size) {
		int min = Math.min(size, bridges.size());
		// mgrigorov: maybe make a copy of the subList to avoid ConcurrentModificationExceptions ?!
		return bridges.subList(start, min).iterator();
	}

	public long countTasks() {
		return bridges.size();
	}

	public static BgProcessSession getSession() {
		return (BgProcessSession)get();
	}
}
