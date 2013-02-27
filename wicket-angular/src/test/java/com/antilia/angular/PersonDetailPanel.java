/**
 * 
 */
package com.antilia.angular;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * 
 * @author reiern70
 * 
 */
public class PersonDetailPanel extends Panel {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public PersonDetailPanel(String id, IModel<Person> model) {
		super(id, model);
		add(new Label("name", Model.of(model.getObject().getName() + " " +model.getObject().getLastName())));
	}
}
