package com.antilia;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.antilia.smartlinks.SmartLinkDataTable;

public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	private Label clicked;

	private String text;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		text = "Nothing clicked yet!";

		clicked = new Label("clicked", new AbstractReadOnlyModel<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getObject() {
				return text;
			}
		} );
		clicked.setOutputMarkupId(true);
		add( clicked );
		List<IColumn<Person, String>> columns = new ArrayList<IColumn<Person,String>>();
		columns.add( new PropertyColumn<Person, String>(Model.of("Name"), "name") ) ;
		columns.add( new PropertyColumn<Person, String>(Model.of("LastName"), "lastName") ) ;
		columns.add( new PropertyColumn<Person, String>(Model.of("Links"), "x") {

			private static final long serialVersionUID = 1L;

			@Override
			public void populateItem(Item<ICellPopulator<Person>> item,
					String componentId, final IModel<Person> rowModel) {
				item.add(new LinksPanel(componentId) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target, int i) {
						Person person = rowModel.getObject();
						text = person.getName()+ "-"+ person.getLastName()+"-Link->>"+ i;
						target.add( clicked );
					}
				});
			}

		});
		add( new SmartLinkDataTable<Person, String>("datatable", columns , new PersonDataProvider(), 20) );
    }
}
