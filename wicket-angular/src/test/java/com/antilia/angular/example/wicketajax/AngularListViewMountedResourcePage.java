package com.antilia.angular.example.wicketajax;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.antilia.angular.WicketApplication;

public class AngularListViewMountedResourcePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public AngularListViewMountedResourcePage(final PageParameters parameters) {
		super(parameters);
		
		add(new PersonsListView("persons", WicketApplication.PERSONS_MOUNT_POINT));

    }
}
