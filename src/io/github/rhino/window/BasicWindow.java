package io.github.rhino.window;

import io.github.rhino.ArrayTree;
import io.github.rhino.Node;
import io.github.rhino.action.Action;
import io.github.rhino.action.PrintAction;
import io.github.rhino.widget.DefaultWidget;
import io.github.rhino.widget.Widget;
import io.github.rhino.widget.WidgetFactory;

public class BasicWindow extends DefaultWidget {

	public BasicWindow(Node node, String widgetName) {
		super(node, widgetName);
		// TODO Auto-generated constructor stub
	}

	public static String getWidgetNameByLayoutRule(DefaultWidget defaultWidget) {
		return defaultWidget.getWidgetName();
	}

	public static void main(String[] argv) {
		ArrayTree tree = new ArrayTree();
		tree.load("GraphSchema");
		BasicWindow window = new BasicWindow(tree.getRoot(), "Basic");
		window.printr();
		System.out.println("=============== Basic ====================");
		// show html interface
		window.show();
	}

	private void show() {
		Widget basicWidget = WidgetFactory.create(this, "PathPhrase");
		basicWidget.forEach(p -> p.size()>2 , new BasicRule());
	}
}
