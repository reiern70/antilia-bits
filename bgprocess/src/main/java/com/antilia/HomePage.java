package com.antilia;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.antilia.panel.LaunchDummyTaskPanel;
import com.antilia.panel.TasksListPanel;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add( new LaunchDummyTaskPanel("launcher") );
		add( new TasksListPanel("tasksLists") );
    }
}
