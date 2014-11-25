package com.antilia;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.wicket.Session;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import com.antilia.service.IAnswerService;
import com.antilia.service.impl.AnswerService;
import com.antilia.task.ExecutionBridge;
import com.antilia.task.ITask;
import com.antilia.task.TasksRunnable;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see com.antilia.Start#main(String[])
 */
public class BgProcessApplication extends WebApplication
{
	ExecutorService executorService =  new ThreadPoolExecutor(10, 10,
			0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());

	/**
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
		com.google.inject.Injector injector = Guice.createInjector(newModule());
		getComponentInstantiationListeners().add( new GuiceComponentInjector(this, injector));
	}

	@Override
	protected void onDestroy() {
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

	private Module newModule() {
		return new Module() {
				public void configure(Binder binder) {
					binder.bind(IAnswerService.class).to(AnswerService.class);
				}
			};
	}

	public static BgProcessApplication getApplication() {
		return (BgProcessApplication)get();
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new BgProcessSession(request);
	}

	public void launch(ITask task) {
		// we are on WEB thread so services should be normally injected.
		ExecutionBridge bridge = new ExecutionBridge();
		// register bridge on session
		bridge.setTaskName("Task-" + BgProcessSession.getSession().countTasks() + 1);
		BgProcessSession.getSession().add(bridge);
		// run the task
		executorService.execute( new TasksRunnable(task, bridge));
	}
}
