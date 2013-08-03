/**
 *
 */
package com.antilia;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

/**
 * @author reiern70
 *
 */
public class PersonDataProvider extends ListDataProvider<Person> implements ISortableDataProvider<Person, String>{

	private static final long serialVersionUID = 1L;

	private static List<Person> persons = new ArrayList<Person>();

	static {
		for(int i=0; i < 1000; i++) {
			Person person = new Person();
			person.setLastName("LastaName" + i);
			person.setName("Name" + i);
			persons.add(person);
		}
	}

	public PersonDataProvider() {
		super( persons );
	}

	private final SingleSortState<String> state = new SingleSortState<String>();

	/**
	 * @see ISortableDataProvider#getSortState()
	 */
	@Override
	public final ISortState<String> getSortState()
	{
		return state;
	}

	/**
	 * Returns current sort state
	 *
	 * @return current sort state
	 */
	public SortParam<String> getSort()
	{
		return state.getSort();
	}

	/**
	 * Sets the current sort state
	 *
	 * @param param
	 *            parameter containing new sorting information
	 */
	public void setSort(final SortParam<String> param)
	{
		state.setSort(param);
	}

	/**
	 * Sets the current sort state
	 *
	 * @param property
	 *            sort property
	 * @param order
	 *            sort order
	 */
	public void setSort(final String property, final SortOrder order)
	{
		state.setPropertySortOrder(property, order);
	}
}
