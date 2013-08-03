/**
 *
 */
package com.antilia.smartlinks;

import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;

/**
 * @author reiern70
 *
 */
public class SmartLinkDataTable<T, S> extends AjaxFallbackDefaultDataTable<T, S> implements ISmartLinkContext {

	private static final long serialVersionUID = 1L;

	public SmartLinkDataTable(String id, List<? extends IColumn<T, S>> columns,
			ISortableDataProvider<T, S> dataProvider, int rowsPerPage) {
		super(id, columns, dataProvider, rowsPerPage);
		setOutputMarkupId( true );
		add( new SmartLinksBehavior() );
	}

	@Override
	public String getContextClass() {
		return getMarkupId();
	}

}
