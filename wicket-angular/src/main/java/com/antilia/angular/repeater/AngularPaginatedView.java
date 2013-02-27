package com.antilia.angular.repeater;

import java.util.Iterator;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.Model;

/**
 * 
 * @author reiern70
 */
public class AngularPaginatedView<B> extends Panel implements IResourceListener {

	private static final long serialVersionUID = 1L;

	private IJSONifier<B> ijsoNifier;
	
	private IDataProvider<B> dataProvider;
	
	private int pageSize;

	private String viewName;

	
	/**
	 * @param id
	 */
	public AngularPaginatedView(String id, IDataProvider<B> dataProvider, int pageSize, IJSONifier<B> ijsoNifier) {
		super(id);
		this.dataProvider = dataProvider;
		this.pageSize = pageSize;
		this.ijsoNifier = ijsoNifier;
		this.setOutputMarkupId(true);
		this.viewName = "AngularRepeatingView"+getMarkupId();
		this.add(new AttributeModifier("ng-controller", Model.of(this.viewName)));
	}
	
	@Override
	public void onResourceRequested() {
		
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		long size = dataProvider.size();
		StringBuilder builder = new StringBuilder();
		builder.append("function ");
		builder.append(viewName);
		builder.append("($scope) {\n");			  
		builder.append("$scope.size=");
		builder.append(size);
		builder.append(";\n");
		builder.append("$scope.elements = [");
		Iterator<? extends B> it = dataProvider.iterator(0, pageSize);
		while(it.hasNext()) {
			builder.append(ijsoNifier.toJSON(it.next()));
			builder.append(",");
		}
		builder.append("];\n");
		builder.append("}\n");
		response.render(JavaScriptHeaderItem.forScript(builder.toString(), null));
	}
	
	
}
