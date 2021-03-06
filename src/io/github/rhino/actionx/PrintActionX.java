package io.github.rhino.actionx;

import java.util.ArrayList;
import java.util.Stack;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.criteria.Criteria;

public class PrintActionX extends DefaultActionX {
	@Override
	public Parameters startParameters() {
		Parameters parameters = new Parameters();
		parameters.put("indent", "");
		return parameters;
	}

	@Override
	public Parameters preNode(Node thisNode, Parameters parameters) {
		super.preNode(thisNode, parameters);
		String indent = parameters.getString("indent");
		if ( indent.length() > 2 ) {
			indent = indent.substring(1, indent.length()-1) + "+";
		}
		System.out.println(indent + "---" + thisNode.getId() + " " + thisNode.getName());
		return parameters;
	}

	//	@Override
	//	public Parameters preChild(Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
	//		String indent = parameters.getString("indent");
	//		Parameters childParameters = new Parameters(parameters);
	//		String childIndent;
	//		if( i < children.size() - 1 ) {
	//			childIndent = indent + "   |";
	//		} else {
	//			childIndent = indent + "    ";
	//		}
	//		childParameters.put("indent", childIndent);
	//		return childParameters;
	//	}
	//
	//	@Override
	//	public Parameters postChild(Node thisNode, int i, ArrayList<Node> children, Parameters parameters, Parameters childParameters) {
	//		return parameters;
	//	}

	@Override
	public Parameters childProcess(Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
		Stack<Node> path = (Stack<Node>) parameters.get("path");
		Criteria criteria = (Criteria) parameters.get("criteria");
		ActionX actionx = (ActionX) parameters.get("actionx");

		Node child = children.get(i);
		String indent = parameters.getString("indent");
		Parameters childParameters = new Parameters(parameters);
		String childIndent;
		if( i < children.size() - 1 ) {
			childIndent = indent + "   |";
		} else {
			childIndent = indent + "    ";
		}
		childParameters.put("indent", childIndent);
		child._forEach(path, childParameters, criteria, actionx);
		return parameters;
	}

	@Override
	public Parameters postNode(Node thisNode, Parameters parameters) {
		return parameters;
	}
}
