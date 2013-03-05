package com.antilia.angular;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.resource.CoreLibrariesContributor;

import com.antilia.angular.example.angularajax.StatelessMasterDetailPage;
import com.antilia.angular.example.wicketajax.AngularListViewMountedResourcePage;
import com.antilia.angular.example.wicketajax.AngularListViewPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		
		add(new BookmarkablePageLink<Void>("link1", AngularListViewPage.class, null));
		add(new BookmarkablePageLink<Void>("link2", AngularListViewMountedResourcePage.class, null));
		add(new BookmarkablePageLink<Void>("link3", StatelessMasterDetailPage.class, null));		
    }
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		CoreLibrariesContributor.contribute(WicketApplication.getApplication(), response);
		response.render(JavaScriptHeaderItem.forUrl("/js/jquery.syntaxhighlighter.min.js"));
		response.render(JavaScriptHeaderItem.forScript("$.SyntaxHighlighter.init();","SyntaxHighlighter"));
	}
}
