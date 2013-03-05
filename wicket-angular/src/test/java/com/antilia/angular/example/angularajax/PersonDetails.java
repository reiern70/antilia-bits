/**
 * 
 */
package com.antilia.angular.example.angularajax;

import com.antilia.angular.WicketApplication;
import com.antilia.angular.example.Person;
import com.antilia.angular.example.PersonsJSonifier;
import com.antilia.angular.repeater.AngularPanel;
import com.antilia.angular.repeater.OnUpdateEventHandler;

/**
 * @author reiern70
 *
 */
public class PersonDetails extends AngularPanel {

	private static final long serialVersionUID = 1L;
	
	private PersonsJSonifier personsJSonifier;
	
	/**
	 * @param id
	 * @param scope
	 */
	public PersonDetails(String id, String scope, PersonsJSonifier personsJSonifier) {
		super(id, scope);
		this.personsJSonifier = personsJSonifier;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		addHandler(new OnUpdateEventHandler<Person>("PersonsList-RowSelected-1") {

			private static final long serialVersionUID = 1L;

			@Override
			protected Person getElement(Long id) {
				return WicketApplication.getApplication().getPersonService().find(id);
			}

			@Override
			protected String getJSON(Person bean) {
				return personsJSonifier.toLONGJSON(bean);
			}
			
		});
	}
	
}
