package com.antilia.angular.example.angularajax;

import java.util.Map;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.template.PackageTextTemplate;

import com.antilia.angular.example.PersonsJSonifier;
import com.antilia.angular.repeater.AngularWebMarkupContainer;
import com.antilia.angular.repeater.IAngularRequestHandler;

public class PureAngularListViewMountedResourcePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public PureAngularListViewMountedResourcePage(final PageParameters parameters) {
		super(parameters);
		
		System.out.println("AAAA");
		AngularWebMarkupContainer personsContext = new AngularWebMarkupContainer("personsContext", "PersonsContext");
		
		personsContext.addHandler(new IAngularRequestHandler() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void handleRequest(Request request, WebResponse response) {
				
			}
			
			@SuppressWarnings("resource")
			@Override
			public String contributeToScope(Map<String, Object> variables) {
				PackageTextTemplate scriptTemplate = new PackageTextTemplate(PureAngularListViewMountedResourcePage.class, "persons.js");
				return scriptTemplate.asString(variables);
			}
			
			@Override
			public boolean canHandleRequest(Request request) {
				return false;
			}
		});

		add(personsContext);
		
		personsContext.add(new PersonsPureAngularListView("persons", "PersonsList", false));
		
		personsContext.add(new PersonDetails("details", "PersonDetails", PersonsJSonifier.getInstance()));		

    }
}
