/**
 *
 */
package com.antilia.smartlinks;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

/**
 * @author reiern70
 *
 */
public abstract class SmartLink extends WebMarkupContainer implements ISmartLink {

	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 */
	public SmartLink(String id) {
		super(id);
		setOutputMarkupId( true );
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		ISmartLinkContext context = findParent(ISmartLinkContext.class);
		if(context != null) {
			add( new AttributeAppender("class", Model.of( "click" + context.getContextClass() ), " " ) );
			add( new AttributeAppender("context", Model.of( context.getContextClass() ), " " ) );
		}
	}
}
