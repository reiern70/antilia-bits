/**
 * 
 */
package com.antilia.angular.example.filtering;

import org.apache.wicket.markup.html.WebPage;

/**
 * @author reiern70
 *
 */
public class FilterPersonListViewPage extends WebPage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 */
	public FilterPersonListViewPage() {
		add(new PersonsListView("persons", true));
	}
}
