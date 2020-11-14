package io.github.rhino.window;

import java.util.Stack;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.Path;
import io.github.rhino.widget.Register;

public class BasicRule implements LayoutRule {

	public String getWidgetName(Parameters parameters) {
		Path path = (Path) parameters.get("path");
		if( path.size() > 0 ) {
			Node theLastNode = path.get(path.size()-1);
			String widgetName = Register.DEFAULT.queryName(theLastNode);
			return widgetName;
		}
		return "Basic";
	}

}
