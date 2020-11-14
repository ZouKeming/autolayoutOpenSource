package io.github.rhino.window;

import io.github.rhino.ArrayTree;
import io.github.rhino.Node;
import io.github.rhino.action.Action;
import io.github.rhino.action.PrintAction;
import io.github.rhino.widget.DefaultWidget;
import io.github.rhino.widget.Widget;
import io.github.rhino.widget.WidgetFactory;

public class DefaultWindow extends DefaultWidget {

	public DefaultWindow(Node node, String widgetName) {
		super(node, widgetName);
		// TODO Auto-generated constructor stub
	}

	public static String getWidgetNameByLayoutRule(Widget widget) {
		return widget.getWidgetName();
	}

	public static void main(String[] argv) {
		ArrayTree tree = new ArrayTree();
		tree.load("GraphSchema");
		DefaultWindow window = new DefaultWindow(tree.getRoot(), "Default");
		window.printr();
		System.out.println("=============== Default ====================");
		// show html interface
		window.show();
	}

	private void show() {
		Widget defaultWidget = WidgetFactory.create(this, "Default");
		defaultWidget.forEach(p -> p.size()>2 , new DefaultRule());
	}
}
