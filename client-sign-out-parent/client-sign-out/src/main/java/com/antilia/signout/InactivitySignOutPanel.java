package com.antilia.signout;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.time.Duration;

/**
 * Hidden panel the will trigger a sign out after certain period of inactivity.
 * This panel should be attached to a page wanting to exhibit such behavior.
 */
public class InactivitySignOutPanel extends GenericPanel<Duration> {

    private final ResourceReference JS = new JavaScriptResourceReference(InactivitySignOutPanel.class, "InactivitySignOutPanel.js");

    private AbstractDefaultAjaxBehavior ajaxBehavior;

    public InactivitySignOutPanel(String id, Duration duration) {
        this(id, Model.of(duration));
    }

    public InactivitySignOutPanel(String id, IModel<Duration> model) {
        super(id, model);
        setRenderBodyOnly(true);
        add(ajaxBehavior = new AbstractDefaultAjaxBehavior() {

            @Override
            protected void respond(AjaxRequestTarget target) {
                WebSession.get().invalidate();
                setResponsePage(WebApplication.get().getApplicationSettings().getPageExpiredErrorPage());
            }

            @Override
            protected void onBind() {
                // panel will not be repainted, no need to markup markup id.
            }

            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                super.renderHead(component, response);
                response.render(JavaScriptHeaderItem.forReference(JS));
            }
        });
    }

    /**
     * @return A function to be executed when time remaining changes.
     */
    protected String getOnTimeChangedFunction() {
        return "function(timeRemaining){}";
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        StringBuilder builder = new StringBuilder();
        builder.append("new Wicket.SignOut(").append(getModel().getObject().getMilliseconds())
                .append(",'").append(ajaxBehavior.getCallbackUrl())
                .append("',").append(getOnTimeChangedFunction()).append(");");
        response.render(OnDomReadyHeaderItem.forScript(builder));

    }
}
