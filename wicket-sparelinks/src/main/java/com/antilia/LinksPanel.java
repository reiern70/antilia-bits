/**
 *
 */
package com.antilia;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import com.antilia.smartlinks.SmartLink;

/**
 * @author reiern70
 *
 */
public abstract class LinksPanel extends Panel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private abstract class IntLink extends SmartLink{
		private static final long serialVersionUID = 1L;

		int value;

		public IntLink(String id, int i) {
			super(id);
			this.value = i;
		}

	}
	/**
	 * @param id
	 */
	public LinksPanel(String id) {
		super(id);

		RepeatingView repeatingView = new RepeatingView("links");
		add(repeatingView);
		for(int i = 0; i< 40; i++) {
			WebMarkupContainer container = new WebMarkupContainer( repeatingView.newChildId() );
			repeatingView.add( container );
			IntLink smartLink = new IntLink("link", i) {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					LinksPanel.this.onClick(target, value);
				}
			};
			container.add( smartLink );
			smartLink.add( new Label("text", i) );
		}
	}

	public abstract void onClick(AjaxRequestTarget target, int i);

}
