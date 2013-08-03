/**
 *
 */
package com.antilia.smartlinks;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 * @author reiern70
 *
 */
public class SmartLinksBehavior extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;

	private static final ResourceReference JS = new JavaScriptResourceReference(SmartLinksBehavior.class, "smartLinksBehavior.js");

	private static final String LINK_ID = "linkid";

	private static class IdVisitor implements IVisitor<Component, Void>, IClusterable {

		private static final long serialVersionUID = 1L;
		private String id;
		private Component component;

		private IdVisitor(String id) {
			this.id = id;
		}

		@Override
		public void component(final Component component, final IVisit<Void> visit) {
			if(id.equals(component.getMarkupId())) {
				this.component = component;
				visit.stop();
			}
		}

		public Component getComponent() {
			return component;
		}
	}

	public SmartLinksBehavior() {
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected void respond(AjaxRequestTarget target) {
		String linkId = RequestCycle.get().getRequest().getRequestParameters().getParameterValue(LINK_ID).toString();
		if( !Strings.isEmpty( linkId ) ) {
			IdVisitor visitor =  new IdVisitor(linkId);
			MarkupContainer container =	(MarkupContainer)getComponent();
			container.visitChildren( visitor );
			Component component = visitor.getComponent();
			if(component instanceof ISmartLink) {
				((ISmartLink)component).onClick(target);
			}
		}
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		if(component instanceof ISmartLinkContext) {
			ISmartLinkContext context = (ISmartLinkContext)component;
			response.render( JavaScriptHeaderItem.forReference( JS ) );
			StringBuilder builder = new StringBuilder();
			builder.append("new Wicket.SmartLink('");
			builder.append(component.getMarkupId());
			builder.append("', '");
			builder.append( context.getContextClass() );
			builder.append("', '");
			builder.append( getCallbackUrl() );
			builder.append("');");
			response.render( OnDomReadyHeaderItem.forScript( builder ) );
		} else {
			throw new IllegalArgumentException("SmartLinksBehavior should be attaced to an instace of ISmartLinkContext!");
		}
	}
}
