/**
 * 
 */
package com.antilia.angular.example.angularajax;

import java.util.Map;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.template.PackageTextTemplate;

import com.antilia.angular.WicketApplication;
import com.antilia.angular.example.Person;
import com.antilia.angular.example.PersonsJSonifier;
import com.antilia.angular.repeater.AngularPanel;
import com.antilia.angular.repeater.IAngularRequestHandler;

/**
 * @author reiern70
 *
 */
public class PersonDetails extends AngularPanel {

	private static final long serialVersionUID = 1L;

	private static final String userID = "userID";
	
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
		addHandler(new IAngularRequestHandler() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void handleRequest(Request request, WebResponse response) {
				response.setContentType("application/json");
				Long personId = Long.parseLong(request.getRequestParameters().getParameterValue(PersonDetails.userID).toString());
				Person person = WicketApplication.getApplication().getPersonService().find(personId);
				if(person != null) {
					response.write(personsJSonifier.toLONGJSON(person));
				} else {
					response.write("{\"error\": \"could not find persons\"}");
				}
			}
			
			@SuppressWarnings("resource")
			@Override
			public String contributeToScope(Map<String, Object> variables) {
				variables.put("userID", userID);
				PackageTextTemplate scriptTemplate = new PackageTextTemplate(PersonsPureAngularListView.class, "persondetails.js");
				return scriptTemplate.asString(variables);
			}
			
			@Override
			public boolean canHandleRequest(Request request) {
				return request.getRequestParameters().getParameterValue(PersonDetails.userID).toString() != null;
			}
		});
	}
	
}
