package com.antilia.angular;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class PureAngularListViewMountedResourcePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public PureAngularListViewMountedResourcePage(final PageParameters parameters) {
		super(parameters);
		
		add(new PersonsPureAngularListView("persons"));

    }
}
