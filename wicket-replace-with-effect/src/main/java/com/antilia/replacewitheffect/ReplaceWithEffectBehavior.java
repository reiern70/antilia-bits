package com.antilia.replacewitheffect;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

/**
 * Behavior that makes a component appear (and disappear) with some jQuery effect when it is 
 * replaced via AJAX.
 * 
 * @author Ernesto Reinaldo Barreiro (reiern70@gmail.com)
 *
 */
public class ReplaceWithEffectBehavior extends Behavior {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @author Effect to be used for hiding element (reiern70).
	 *
	 */
	public static interface IHideEffect extends Serializable {
	
		String render(String notifyName);
	}
	
	
	/**
	 * 
	 * @author Effect to be used for showing element (reiern70).
	 *
	 */
	public static interface IShowEffect extends Serializable {
	
		String render();
	}
	
	/**
	 * Base hide effect.
	 * 
	 * @author reiern70
	 *
	 */
	public static class BaseHideEffect implements IHideEffect {
		
		private static final long serialVersionUID = 1L;

		private String name; 
		private int duration;
		
		public BaseHideEffect(String name) {
			this(name, 500);
		}
		
		public BaseHideEffect(String name, int duration) {
			this.name = name;
			this.duration = duration;
		}
		
		@Override
		public String render(String notifyName) {
			return "."+getName()+"("+getDuration()+","+notifyName+");" ;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getDuration() {
			return duration;
		}

		public void setDuration(int duration) {
			this.duration = duration;
		}
	}

	/**
	 * Base hide effect.
	 * 
	 * @author reiern70
	 *
	 */
	public static class BaseShowEffect implements IShowEffect {
		
		private static final long serialVersionUID = 1L;

		private String name; 
		private int duration;
		
		public BaseShowEffect(String name) {
			this(name, 500);
		}
		
		public BaseShowEffect(String name, int duration) {
			this.name = name;
			this.duration = duration;
		}
		
		@Override
		public String render() {
			return "."+getName()+"("+getDuration()+");" ;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getDuration() {
			return duration;
		}

		public void setDuration(int duration) {
			this.duration = duration;
		}
	}
	
	/**
	 * Fade in effect.
	 * 
	 * @author reiern70
	 *
	 */
	public static class FadeInEffect extends BaseShowEffect {
		
		private static final long serialVersionUID = 1L;

		public FadeInEffect() {
			this(400);
		}
		
		public FadeInEffect(int duration) {
			super("fadeIn", duration);
		}		
	}
	
	
	/**
	 * http://api.jquery.com/slideDown/
	 * 
	 * @author reiern70
	 *
	 */
	public static class SlideDownEffect extends BaseShowEffect {
		
		private static final long serialVersionUID = 1L;

		public SlideDownEffect() {
			this(400);
		}
		
		public SlideDownEffect(int duration) {
			super("slideDown", duration);
		}		
	}
	
	/**
	 * http://api.jquery.com/fadeOut/
	 * 
	 * @author reiern70
	 *
	 */
	public static class FadeOutEffect extends BaseHideEffect {
		
		private static final long serialVersionUID = 1L;

		public FadeOutEffect() {
			this(400);
		}
		
		public FadeOutEffect(int duration) {
			super("fadeOut", duration);
		}		
	}
		
		
	/**
	 * //http://api.jquery.com/slideUp/
	 * 
	 * @author reiern70
	 *
	 */
	public static class SlideUpEffect extends BaseHideEffect {
		
		private static final long serialVersionUID = 1L;

		public SlideUpEffect() {
			this(400);
		}
		
		public SlideUpEffect(int duration) {
			super("slideUp", duration);
		}		
	}

	/**
	 * 
	 */
	private IHideEffect hideEffect;
	
	/**
	 * 
	 */
	private IShowEffect showEffect;
	
	public ReplaceWithEffectBehavior() {
		this(new FadeOutEffect(), new FadeInEffect());
	}
	/**
	 * Constructor.
	 * @param hideEffect
	 * @param showEffect
	 */
	public ReplaceWithEffectBehavior(IHideEffect hideEffect, IShowEffect showEffect) {
		this.hideEffect = hideEffect;
		this.showEffect = showEffect;
	}
	
	@Override
	public void bind(Component component) {
		component.setOutputMarkupId(true);
	}

	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		String style = tag.getAttribute("style");
		if(style == null ||  style.indexOf("display: none;") < 0) {
			tag.append("style", "display: none;", ";");
		}
	}
	
	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		AjaxRequestTarget target = component.getRequestCycle().find(AjaxRequestTarget.class);
		if(target != null) {
			target.prependJavaScript("notify|if(jQuery('#" + component.getMarkupId() + "').length != 0){jQuery('#" + component.getMarkupId() + "')"+hideEffect.render("notify")+";} else {notify();}");
			target.appendJavaScript("jQuery('#"+component.getMarkupId()+"')"+showEffect.render()+";");
		} else {
			response.render(OnDomReadyHeaderItem.forScript("jQuery('#"+component.getMarkupId()+"')"+showEffect.render()+";"));
		}
	}
}
