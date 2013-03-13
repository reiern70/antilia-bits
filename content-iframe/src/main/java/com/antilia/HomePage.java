package com.antilia;

import org.apache.wicket.core.util.resource.PackageResourceStream;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.antilia.iframe.DocumentInlineFrame;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new DocumentInlineFrame("test", new PackageResourceStream(HomePage.class, "ScalaOverview.pdf")));

    }
}
