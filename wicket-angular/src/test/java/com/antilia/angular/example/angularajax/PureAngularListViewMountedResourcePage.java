package com.antilia.angular.example.angularajax;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.antilia.angular.example.PersonsJSonifier;
import com.antilia.angular.repeater.AngularWebMarkupContainer;
import com.antilia.angular.repeater.EventBroadcasterHandler;

public class PureAngularListViewMountedResourcePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public PureAngularListViewMountedResourcePage(final PageParameters parameters) {
		super(parameters);
		
		AngularWebMarkupContainer personsContext = new AngularWebMarkupContainer("personsContext", "PersonsContext");
		
		personsContext.addHandler(new EventBroadcasterHandler("PersonsList-RowSelected","PersonsList-RowSelected-1"));

		add(personsContext);
		
		personsContext.add(new PersonsPureAngularListView("persons", "PersonsList", false));
		
		personsContext.add(new PersonDetails("details", "PersonDetails", PersonsJSonifier.getInstance()));		

    }
}
