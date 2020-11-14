package io.github.rhino.widget;

import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.Path;
import io.github.rhino.criteria.CriteriaPrmt;
import io.github.rhino.window.LayoutRule;
import io.github.util.Misc;

public class PathPhraseWidget extends BasicWidget {
	public PathPhraseWidget(Node node, String widgetName) {
		super(node, widgetName);
		Register.DEFAULT.register(widgetName, this);
	}

	@Override
	public Parameters preNode(Path path, Node thisNode, Parameters parameters) {
		String indent = parameters.getString("indent");
		Path pathPhrase = (Path) parameters.get("pathPhrase");
		pathPhrase.push(thisNode);
		
		if ( indent.length() > 2 ) {
			indent = indent.substring(1, indent.length()-1) + "+";
		}

		ArrayList<Node> children = thisNode.getChildren();
		Node firstNode = pathPhrase.get(0);
		String phrase = Misc.Path2String(pathPhrase);
		if(children==null)
			System.out.println(indent + "-- " + firstNode.getId() + " " + phrase);
		else {
			if(children.size() == 0 || children.size() > 1)
				System.out.println(indent + "-- " + firstNode.getId() + " " + phrase);
		}
		return parameters;
	}

	@Override
	public Parameters preChild(Path path, Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
		String indent = parameters.getString("indent");
		Path pathPhrase = (Path) parameters.get("pathPhrase");
		Parameters childParameters = new Parameters(parameters);
		String childIndent;

		if(children.size()==1) {
		} else {
			if( i < children.size() - 1 ) {
				childIndent = indent + "   |";
			} else {
				childIndent = indent + "    ";
			}
			childParameters.put("indent", childIndent);
			childParameters.put("pathPhrase", new Path());
		}
		return childParameters;
	}


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
				Widget childWidget = WidgetFactory.create(child, layoutRule.getWidgetName(path));
				childParameters.put("thisNode", childWidget);
				childWidget._forEach(path, childParameters, criteria, layoutRule);

				postChild(path, this, i, children, parameters, childParameters);			
			}
		} 

		postNode(path, thisNode, parameters);
		path.pop();
	}
}
