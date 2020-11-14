package io.github.rhino.window;

import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.Node;

public class ColumnRule implements LayoutRule {

	public String getWidgetName(Parameters parameters) {
		Node thisNode = (Node) parameters.get("thisNode");
		boolean isUiNode = parameters.getBoolean("isUiNode");
		ArrayList<Node> children = thisNode.getChildren();
		if( thisNode.getId()==1 ) {
			return "ColumnSVGH";
		} else if ( thisNode.getId()==5 ) {
			return "ColumnSVGH";
		} else if ( thisNode.getId()==8 ) {
			return "ColumnSVGH";
		} else if( (
				thisNode.getId()) % 3 == 1 
				&& thisNode.getId() > 10
				&& (children!=null && children.size() > 1 ) 
				) {
			return "TabSVGH";
		} else {
			return "DocumentorSVGH";
		}
	}

}
