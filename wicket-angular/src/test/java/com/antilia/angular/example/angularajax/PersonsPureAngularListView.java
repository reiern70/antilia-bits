/**
 * 
 */
package com.antilia.angular.example.angularajax;

import com.antilia.angular.WicketApplication;
import com.antilia.angular.example.Person;
import com.antilia.angular.example.PersonsJSonifier;
import com.antilia.angular.repeater.AngularListView;

/**
 * @author reiern70
 *
 */
public class PersonsPureAngularListView extends AngularListView<Person> {

	private static final long serialVersionUID = 1L;
			
	
	/**
	 * Constructor using a page based service.
	 * @param id
	 */
	public PersonsPureAngularListView(String id,  String scope, boolean lazy) {
		super(id, WicketApplication.PERSONS, PersonsJSonifier.getInstance(), scope , lazy);			
	}
	
	
	@Override
	protected void contributeToScope(StringBuilder scope) {
		scope.append("$scope.orderProp = 'age';\n");				
	}
}
