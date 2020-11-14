package io.github.rhino.widget;

import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.Path;
import io.github.rhino.actionp.ActionP;
import io.github.rhino.criteria.Criteria;
import io.github.rhino.criteria.CriteriaPrmt;
import io.github.rhino.window.LayoutRule;
import io.github.util.Misc;

public class DocumentorWidget extends Widget implements ActionP {
	private static final int LINESIZE = 80;
	private String widgetName;

	public DocumentorWidget(Node node, String widgetName) {
		super(node, widgetName);
		Register.DEFAULT.register(widgetName, this);
	}

	@Override
	public Parameters startParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Parameters preNode(Path path, Node thisNode, Parameters parameters) {
		String indent = parameters.getString("indent");
		Boolean isLastNode = parameters.getBoolean("isLastNode");
		Path pathPhrase = (Path) parameters.get("pathPhrase");
		String delimiter = " ";
		pathPhrase.push(thisNode);

		ArrayList<Node> children = thisNode.getChildren();
		Node firstNode = pathPhrase.get(0);
		String phrase = Misc.Path2String(pathPhrase, delimiter);
		if(phrase.length() > LINESIZE) {
			// TODO
		}
		String text = firstNode.getId() + delimiter + phrase;
		if(children==null) {
			printNode(indent, "--", firstNode.getId() + delimiter + phrase, isLastNode);
		} else {
			if(children.size() == 0 || children.size() > 1) {
				printNode(indent, "--", firstNode.getId() + delimiter + phrase, isLastNode);
			}
		}
		return parameters;
	}

	@Override
	public Parameters preChild(Path path, Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
		String indent = parameters.getString("indent");
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
	public void _forEach(Path path, Parameters parameters, CriteriaPrmt criteria, LayoutRule layoutRule) {
		int level = parameters.getInteger("level");
		String indent = parameters.getString("indent");
		Node thisNode = (Node)parameters.get("thisNode");
		if(path.contains(this) || criteria.exit(parameters))
			return;

		path.push(thisNode);
		if( thisNode.getName().equals(" ") && level > 0 ) {
			Boolean isLastNode = parameters.getBoolean("isLastNode");
			DocumentorWidget childWidget = (DocumentorWidget) WidgetFactory.create(thisNode, "Documentor");
			childWidget.textOut(path, parameters, criteria, layoutRule, isLastNode);
		} else {
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
					if( i == children.size() - 1) {
						childParameters.put("isLastNode", true);
					}
					childWidget._forEach(path, childParameters, criteria, layoutRule);

					postChild(path, this, i, children, parameters, childParameters);			
				}
			} 
			postNode(path, thisNode, parameters);
		}

		path.pop();
	}

	//
	//  backup solution 2. insert in the loop
	//
	//	public void _forEach(Path path, Parameters parameters, Criteria criteria, LayoutRule layoutRule) {
	//		int level = parameters.getInteger("level");
	//		String indent = parameters.getString("indent");
	//		Node thisNode = (Node)parameters.get("thisNode");
	//		if(path.contains(this) || criteria.exit(path))
	//			return;
	//
	//		path.push(thisNode);
	//		preNode(path, thisNode, parameters);
	//
	//		ArrayList<Node> children = thisNode.getChildren();
	//		if(children!=null) {
	//			for(int i=0; i<children.size(); i++) {
	//				Node child = children.get(i);
	//				Parameters childParameters = preChild(path, this, i, children, parameters);
	//
	//				//
	//				// create child with the layout Rule
	//				//
	//
	//				if( child.getName().equals(" ") && level > 0 ) {
	//					DocumentorWidget childWidget = (DocumentorWidget) WidgetFactory.create(child, "Documentor");
	//					childWidget.textOut(path, parameters, criteria, layoutRule);
	//				} else {
	//					Widget childWidget = WidgetFactory.create(child, layoutRule.getWidgetName(path));
	//					childParameters.put("thisNode", childWidget);
	//					childWidget._forEach(path, childParameters, criteria, layoutRule);
	//				}
	//
	//				postChild(path, this, i, children, parameters, childParameters);			
	//			}
	//		} 
	//		postNode(path, thisNode, parameters);
	//
	//		path.pop();
	//	}

	//
	// terminate widget stack. All of subnodes will be treated as a word of document.
	//
	public void textOut(Path path, Parameters parameters, CriteriaPrmt criteria, LayoutRule layoutRule, Boolean isLastNode) {
		String indent = parameters.getString("indent");
		StringBuilder sb = new StringBuilder();
		_textOut(path, parameters, criteria, layoutRule, sb);
		printNode(indent, "--", sb.toString(), isLastNode);
		System.out.println(indent +  "    " );
	}

	public void _textOut(Path path, Parameters parameters, CriteriaPrmt criteria, LayoutRule layoutRule, StringBuilder sb) {
		Node thisNode = (Node)parameters.get("thisNode");
		sb.append(thisNode.getName() + " ");

		ArrayList<Node> children = thisNode.getChildren();
		if(children!=null) {
			for(int i=0; i<children.size(); i++) {
				Node child = children.get(i);

				Parameters childParameters = new Parameters(parameters);
				//
				// create child with the layout Rule
				//
				DocumentorWidget childWidget = (DocumentorWidget) WidgetFactory.create(child, "Documentor");
				childParameters.put("thisNode", childWidget);
				childWidget.__textOut(path, childParameters, criteria, layoutRule, sb);
			}
		} 
	}

	public void __textOut(Path path, Parameters parameters, CriteriaPrmt criteria, LayoutRule layoutRule, StringBuilder sb) {
		Node thisNode = (Node)parameters.get("thisNode");
		if(path.contains(this) || criteria.exit(path))
			return;

		path.push(thisNode);
		sb.append(thisNode.getName() + " ");

		ArrayList<Node> children = thisNode.getChildren();
		if(children!=null) {
			for(int i=0; i<children.size(); i++) {
				Node child = children.get(i);

				Parameters childParameters = new Parameters(parameters);
				//
				// create child with the layout Rule
				//
				DocumentorWidget childWidget = (DocumentorWidget) WidgetFactory.create(child, "Documentor");
				childParameters.put("thisNode", childWidget);
				childWidget.__textOut(path, childParameters, criteria, layoutRule, sb);
			}
		} 
		path.pop();
	}

	@Override
	public Parameters postChild(Path path, Node thisNode, int i, ArrayList<Node> children, Parameters parameters,
			Parameters childParameters) {
		return parameters;
	}

	@Override
	public Parameters postNode(Path path, Node thisNode, Parameters parameters) {
		return parameters;
	}
}
