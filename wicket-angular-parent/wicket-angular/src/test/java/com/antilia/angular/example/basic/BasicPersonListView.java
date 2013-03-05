/**
 * 
 */
package com.antilia.angular.example.basic;

import com.antilia.angular.WicketApplication;
import com.antilia.angular.example.Person;
import com.antilia.angular.example.PersonsJSonifier;
import com.antilia.angular.repeater.AngularListView;

/**
 * @author reiern70
 *
 */
public class BasicPersonListView extends AngularListView<Person> {

	private static final long serialVersionUID = 1L;

	public BasicPersonListView(String id, boolean lazy) {
		super(id, WicketApplication.PERSONS, PersonsJSonifier.getInstance(), "Persons", lazy);
	}
	
}
