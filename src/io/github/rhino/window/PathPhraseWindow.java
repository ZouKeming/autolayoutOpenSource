package io.github.rhino.window;

import java.io.File;

import io.github.rhino.ArrayTree;
import io.github.rhino.Node;
import io.github.rhino.RandomTree;
import io.github.rhino.widget.DefaultWidget;
import io.github.rhino.widget.Widget;
import io.github.rhino.widget.WidgetFactory;

public class PathPhraseWindow extends DefaultWidget {

	public PathPhraseWindow(Node node, String widgetName) {
		super(node, widgetName);
		// TODO Auto-generated constructor stub
	}

	public static String getWidgetNameByLayoutRule(DefaultWidget defaultWidget) {
		return defaultWidget.getWidgetName();
	}

	public static void main(String[] argv) {
		RandomTree random = new RandomTree();
		random.testGraphInit();
		
		File[] files = new File(".").listFiles();
		for(File file : files) {
			String filename = file.getName();
			if (filename.matches("GraphSchema.f[^-]*")) {
				ArrayTree tree = new ArrayTree();
				tree.load(filename);
				PathPhraseWindow window = new PathPhraseWindow(tree.getRoot(), "PathPhrase");
//				window.printr();
				System.out.printf("\n=============== PathPhrase Window  %s ====================\n", filename);
				// show html interface
				window.show();
				
			}
		}
	}

	private void show() {
		Widget basicWidget = WidgetFactory.create(this, "PathPhrase");
		basicWidget.forEach(p -> p.size()>99 , new BasicRule());
	}
}
