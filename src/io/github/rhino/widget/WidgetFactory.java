package io.github.rhino.widget;

import io.github.rhino.Node;
import io.github.rhino.httpd.hwidget.ColumnSVGHWidget;
import io.github.rhino.httpd.hwidget.DefaultHWidget;
import io.github.rhino.httpd.hwidget.DocumentorHWidget;
import io.github.rhino.httpd.hwidget.DocumentorSVGHWidget;
import io.github.rhino.httpd.hwidget.NopSVGHWidget;
import io.github.rhino.httpd.hwidget.TabSVGHWidget;

public class WidgetFactory {
	public static Widget create(Node root, String widgetName) {
		Widget widget=null;
		if(widgetName.equals("Default")) {
			widget = new DefaultWidget(root, widgetName);
		} else if( widgetName.equals("Basic")) {
			widget = new BasicWidget(root, widgetName);
		} else if( widgetName.equals("PathPhrase")) {
			widget =  new PathPhraseWidget(root, widgetName);
		} else if( widgetName.equals("Documentor")) {
			widget =  new DocumentorWidget(root, widgetName);
//		} else if( widgetName.equals("DefaultH")) {
//			widget =  new DefaultHWidget(root, widgetName);
//		} else if( widgetName.equals("DocumentorH")) {
//			widget =  new DocumentorHWidget(root, widgetName);
//		} else if( widgetName.equals("DocumentorSVGH")) {
//			widget =  new DocumentorSVGHWidget(root, widgetName);
//		} else if( widgetName.equals("TabSVGH")) {
//			widget =  new TabSVGHWidget(root, widgetName);
//		} else if( widgetName.equals("NopSVGH")) {
//			widget =  new NopSVGHWidget(root, widgetName);
//		} else if( widgetName.equals("ColumnSVGH")) {
//			widget =  new ColumnSVGHWidget(root, widgetName);
		} 
		return widget;
	}
}
