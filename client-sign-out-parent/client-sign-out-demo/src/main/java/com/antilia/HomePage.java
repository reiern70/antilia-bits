package com.antilia;

import com.antilia.signout.InactivitySignOutPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.time.Duration;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

	    add(new InactivitySignOutPanel("logOut", Duration.seconds(10)) {
            protected String getOnTimeChangedFunction() {
                return "function(timeRemaining){if(!window.countDown) { window.countDown = $('#countDown'); }  $(window.countDown).html(timeRemaining);}";
            }
        });

        add( new AjaxLink<Void>("link") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                // do nothing
            }
        });

    }
}
