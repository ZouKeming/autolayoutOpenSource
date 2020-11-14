package io.github.rhino.window;

import java.util.Stack;

import io.github.rhino.Node;

public class MixDefaultAndBasicRule implements LayoutRule {

	public String getWidgetName(Stack<Node> path) {
		if( path.size() % 2 == 1 )
			return "Default";
		else 
			return "Basic";
	}

}
