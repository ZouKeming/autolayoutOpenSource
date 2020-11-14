package io.github.rhino.window;

import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.Node;

public class TabRandomRule implements LayoutRule {

	public String getWidgetName(Parameters parameters) {
		Node thisNode = (Node) parameters.get("thisNode");
		boolean isUiNode = parameters.getBoolean("isUiNode");
		ArrayList<Node> children = thisNode.getChildren();
		if( (thisNode.getId()) % 3 == 1 && (children!=null && children.size() > 1 ) ) {
			return "TabSVGH";
		} else {
			return "DocumentorSVGH";
		}
	}

}
