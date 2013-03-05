/**
 * 
 */
package com.antilia.angular.example.basic;

import org.apache.wicket.markup.html.WebPage;

/**
 * @author reiern70
 *
 */
public class BasicListViewPage extends WebPage {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 */
	public BasicListViewPage() {
		add(new BasicPersonListView("persons", false));
	}
}
