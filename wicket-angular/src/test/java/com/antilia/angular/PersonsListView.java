/**
 * 
 */
package com.antilia.angular;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;

import com.antilia.angular.repeater.AngularListView;

/**
 * @author reiern70
 *
 */
public class PersonsListView extends AngularListView<Person> {

	private static final long serialVersionUID = 1L;
	
	private ModalWindow modalWindow;
	
	private static final String userID = "userID";
	
	private AbstractDefaultAjaxBehavior ajaxBehavior;
	public PersonsListView(String id) {
		super(id, WicketApplication.PERSONS, PersonsJSonifier.getInstance());
		Form<Person> form = new Form<Person>("form");		
		add(form);
		form.add(modalWindow = new ModalWindow("modalWindow"));
		
		add(ajaxBehavior = new AbstractDefaultAjaxBehavior() {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target) {
				String personId = RequestCycle.get().getRequest().getRequestParameters().getParameterValue(userID).toString();
				Person person = WicketApplication.getApplication().getPersonService().find(Long.parseLong(personId));
				modalWindow.setContent(new PersonDetailPanel(modalWindow.getContentId(), Model.of(person)));
				modalWindow.show(target);
			}
		});				
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		
		StringBuilder scope = new StringBuilder();
		scope.append("ajaxUrl = '");
		scope.append(ajaxBehavior.getCallbackUrl());		
		scope.append("';\n");
		scope.append("showDetails = function(id) {\n");
		scope.append("var url = ajaxUrl;\n");		
		scope.append("Wicket.Ajax.get({'u':url, 'ep': {'userID': id}});");
		scope.append("}\n");
		response.render(OnDomReadyHeaderItem.forScript(scope));
	}
	
	@Override
	protected void contributeToScope(StringBuilder scope) {
		scope.append("$scope.orderProp = 'age';\n");
	
	}
}