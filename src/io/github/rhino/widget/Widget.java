package io.github.rhino.widget;

import java.io.PrintWriter;
import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.Path;
import io.github.rhino.actionp.ActionP;
import io.github.rhino.criteria.Criteria;
import io.github.rhino.criteria.CriteriaPrmt;
import io.github.rhino.httpd.hwidget.WidgetBox;
import io.github.rhino.window.LayoutRule;

public abstract class Widget extends Node implements ActionP{
	
	private String widgetName;

	public Widget(Node node, String widgetName) {
		super(node);
		this.widgetName = widgetName;
	}
	
	@Override
	public Parameters startParameters() {
		Parameters parameters = new Parameters();
		return parameters;
	}

	@Override
	public Parameters preNode(Path path, Node thisNode, Parameters parameters) {
		return parameters;
	}
	
	@Override
	public Parameters preChild(Path path, Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
		return parameters;
	}

	public void forEach(CriteriaPrmt criteria, LayoutRule layoutRule) {
		Parameters parameters = LayoutRule.startParameters();
		Path path = new Path();
		parameters.put("path", path);
		parameters.put("thisNode", this);
		Path pathPhrase = new Path();
		parameters.put("pathPhrase", pathPhrase);
		_forEach(path, parameters, criteria, layoutRule);
	}

	public void forEach(PrintWriter printWriter, CriteriaPrmt criteria, LayoutRule layoutRule) {
		Parameters parameters = LayoutRule.startParameters();
		Path path = new Path();
		parameters.put("path", path);
		parameters.put("thisNode", this);
		Path pathPhrase = new Path();
		parameters.put("pathPhrase", pathPhrase);
		parameters.put("printWriter", printWriter);
		_forEach(path, parameters, criteria, layoutRule);
	}

	public void forEach(PrintWriter printWriter, CriteriaPrmt criteria, LayoutRule layoutRule, int layoutWidth) {
		Parameters parameters = LayoutRule.startParameters();
		Path path = new Path();
		parameters.put("path", path);
		parameters.put("thisNode", this);
		Path pathPhrase = new Path();
		parameters.put("pathPhrase", pathPhrase);
		parameters.put("printWriter", printWriter);
//		parameters.put("layoutWidth", layoutWidth);
		parameters.put("uiLevel", 0);
		WidgetBox widgetBox = new WidgetBox(0, 0, layoutWidth, 8000);
		parameters.put("widgetBox", widgetBox);
		parameters.put("xOffset", 13);
		_forEach(path, parameters, criteria, layoutRule);
	}

	public abstract void _forEach(Path path, Parameters parameters, CriteriaPrmt criteria, LayoutRule layoutRule);
	
	@Override
	public Parameters postChild(Path path, Node thisNode, int i, ArrayList<Node> children, Parameters parameters,
			Parameters childParameters) {
		return parameters;
	}

	@Override
	public Parameters postNode(Path path, Node thisNode, Parameters parameters) {
		return parameters;
	}

	public String getWidgetName() {
		return widgetName;
	}

	public void setWidgetName(String widgetName) {
		this.widgetName = widgetName;
	}

}
