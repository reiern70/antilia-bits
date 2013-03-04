package com.antilia.angular.example.wicketajax;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;


public class AngularListViewPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public AngularListViewPage(final PageParameters parameters) {
		super(parameters);
		
		add(new PersonsListView("persons", false));

    }
}
