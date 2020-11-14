package io.github.rhino.widget;

import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.Path;
import io.github.rhino.criteria.CriteriaPrmt;
import io.github.rhino.window.LayoutRule;

public class DefaultWidget extends Widget implements Cloneable {
	public DefaultWidget(Node node, String widgetName) {
		super(node, widgetName);
		Register.DEFAULT.register(widgetName, this);
	}

	//
	// print +-- style from here
	//	---0 ziuag
	//	  +---1 oqimux
	//	  |   +---2 okuel
	//	  |   +---3 ooivuful
	//	  |   +---22 moiiw
	//	  +---4 abaoohen
	//	  |   +---14 oiu
	//	  |   +---18 ir
	//	  +---16 zif
	// 
	
	@Override
	public Parameters preNode(Path path, Node thisNode, Parameters parameters) {
		String indent = parameters.getString("indent");
		if ( indent.length() > 2 ) {
			indent = indent.substring(0, indent.length()-1) + "+";
		}
		System.out.println(indent + "-- " + thisNode.getId() + " " + thisNode.getName());
		return parameters;
	}

	//
	// end of style define
	//
	
	//
	// Recursively traverse all widget nodes
	//
	@Override
	public void _forEach(Path path, Parameters parameters, CriteriaPrmt criteria, LayoutRule layoutRule) {
		Node thisNode = (Node)parameters.get("thisNode");
		if(path.contains(this) || criteria.exit(parameters))
			return;
	
		path.push(thisNode);
		preNode(path, thisNode, parameters);
	
		ArrayList<Node> children = thisNode.getChildren();
		if(children!=null) {
			for(int i=0; i<children.size(); i++) {
				Node child = children.get(i);
				Parameters childParameters = preChild(path, this, i, children, parameters);
				
				//
				// create child with the layout Rule
				//
				Widget childWidget = WidgetFactory.create(child, layoutRule.getWidgetName(parameters));
				childParameters.put("thisNode", childWidget);
				childWidget._forEach(path, childParameters, criteria, layoutRule);
				
				postChild(path, this, i, children, parameters, childParameters);			
			}
		}
	
		postNode(path, thisNode, parameters);
		path.pop();
	}

	@Override
	public Parameters preChild(Path path, Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
		String indent = parameters.getString("indent");
		Parameters childParameters = new Parameters(parameters);
		String childIndent;
		if( i < children.size() - 1 ) {
			childIndent = indent + "   |";
		} else {
			childIndent = indent + "    ";
		}
		childParameters.put("indent", childIndent);
		return childParameters;
	}
	//
	// end of style define
	//
}
