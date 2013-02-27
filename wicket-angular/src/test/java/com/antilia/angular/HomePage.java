package com.antilia.angular;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		
		add(new BookmarkablePageLink<Void>("link1", AngularListViewPage.class, null));
		add(new BookmarkablePageLink<Void>("link2", AngularListViewMountedResourcePage.class, null));
    }
}
