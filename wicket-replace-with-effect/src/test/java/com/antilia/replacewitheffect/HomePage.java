package com.antilia.replacewitheffect;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	private WebMarkupContainer slideDown;
	/**
	 * 
	 */
	public HomePage() {
		add(slideDown = new WebMarkupContainer("slideDown"));
		slideDown.add(new ReplaceWithEffectBehavior());
		add(new AjaxLink<Void>("link") {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.add(slideDown);
			}
		});
	}

}
