/**
 * 
 */
package com.antilia.angular.example.filtering;

import com.antilia.angular.WicketApplication;
import com.antilia.angular.example.Person;
import com.antilia.angular.example.PersonsJSonifier;
import com.antilia.angular.repeater.AngularListView;

/**
 * @author reiern70
 *
 */
public class PersonsListView extends AngularListView<Person> {

	private static final long serialVersionUID = 1L;
	

	/**
	 * Constructor using a page based service.
	 * @param id
	 */
	public PersonsListView(String id, boolean lazy) {
		super(id, WicketApplication.PERSONS, PersonsJSonifier.getInstance(), null, lazy);					
	}
	
	/**
	 * Constructor using 
	 * 
	 * @param id
	 * @param mountPath
	 */
	public PersonsListView(String id, String mountPath) {
		super(id, mountPath, null);					
	}
	
	
	@Override
	protected void contributeToScope(StringBuilder scope) {
		scope.append("$scope.orderProp = 'age';\n");
	
	}
}
