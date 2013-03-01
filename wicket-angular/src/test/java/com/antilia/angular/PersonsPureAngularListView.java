/**
 * 
 */
package com.antilia.angular;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.template.PackageTextTemplate;

import com.antilia.angular.repeater.AngularListView;
import com.antilia.angular.repeater.IAngularRequestHandler;

/**
 * @author reiern70
 *
 */
public class PersonsPureAngularListView extends AngularListView<Person> {

	private static final long serialVersionUID = 1L;
		
	private static final String userID = "userID";
	
	/**
	 * Constructor using a page based service.
	 * @param id
	 */
	public PersonsPureAngularListView(String id) {
		super(id, WicketApplication.PERSONS, PersonsJSonifier.getInstance());			
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		addHandler(new IAngularRequestHandler() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void handleRequest(Request request, WebResponse response) {
				response.setContentType("application/json");
				Long personId = Long.parseLong(request.getRequestParameters().getParameterValue(PersonsPureAngularListView.userID).toString());
				Person person = WicketApplication.getApplication().getPersonService().find(personId);
				if(person != null) {
					response.write(((PersonsJSonifier)getIjsoNifier()).toLONGJSON(person));
				} else {
					response.write("{\"error\": \"could not find persons\"}");
				}
			}
			
			@Override
			public boolean canHandleRequest(Request request) {
				return request.getRequestParameters().getParameterValue(PersonsPureAngularListView.userID).toString() != null;
			}
		});
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);	
		
		CharSequence url = urlFor(IResourceListener.INTERFACE, null);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("url", url);
		variables.put("userID", userID);
		PackageTextTemplate scriptTemplate = new PackageTextTemplate(PersonsPureAngularListView.class, "persondetails.js");
		response.render(JavaScriptHeaderItem.forScript(scriptTemplate.asString(variables), getMarkupId()));
		try {
			scriptTemplate.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	
	
	@Override
	protected void contributeToScope(StringBuilder scope) {
		scope.append("$scope.orderProp = 'age';\n $scope.selected=null;");
		scope.append("$scope.selectRow = function(id) {");
		scope.append("$scope.selected = id; ");
		scope.append("var args = {'selected': $scope.elements[id].id};");
		scope.append("$rootScope.$broadcast('RowSelected', args); ");
		scope.append("	} ");
		
	}
}
