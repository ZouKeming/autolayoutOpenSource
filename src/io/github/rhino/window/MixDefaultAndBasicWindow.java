package io.github.rhino.window;

import java.io.File;

import io.github.rhino.ArrayTree;
import io.github.rhino.Node;
import io.github.rhino.widget.DefaultWidget;
import io.github.rhino.widget.Widget;
import io.github.rhino.widget.WidgetFactory;

public class MixDefaultAndBasicWindow extends DefaultWidget {

	public MixDefaultAndBasicWindow(Node node, String widgetName) {
		super(node, widgetName);
		// TODO Auto-generated constructor stub
	}

	public static String getWidgetNameByLayoutRule(DefaultWidget defaultWidget) {
		return defaultWidget.getWidgetName();
	}
	
	public static void test() {
		ArrayTree tree = new ArrayTree();
		tree.load("GraphSchema");
		MixDefaultAndBasicWindow window = new MixDefaultAndBasicWindow(tree.getRoot(), "Default");
		window.printr();
		System.out.println("===================================");
		// show html interface
		window.show();
	}
	
	public static void test2() {
		File[] files = new File(".").listFiles();
		for(File file : files) {
			if(file.getName().matches("GraphSchema.f.*")) {
				ArrayTree tree = new ArrayTree();
				tree.load(file.getName());
				
				MixDefaultAndBasicWindow window = new MixDefaultAndBasicWindow(tree.getRoot(), "Default");
				window.show();
				
				System.out.println();
			}
		}
		
	}

	public static void main(String[] argv) {
		test2();
	}

	private void show() {
		Widget basicWidget = WidgetFactory.create(this, "Basic");
		basicWidget.forEach(p -> p.size()>99 , new MixDefaultAndBasicRule());
	}
}
