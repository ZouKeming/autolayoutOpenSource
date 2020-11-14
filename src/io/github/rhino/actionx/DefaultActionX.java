package io.github.rhino.actionx;

import java.util.ArrayList;
import java.util.Stack;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.criteria.Criteria;

public class DefaultActionX implements ActionX {
	@Override
	public Parameters startParameters() {
		Parameters parameters = new Parameters();
		return parameters;
	}

	@Override
	public Parameters preNode(Node thisNode, Parameters parameters) {
		parameters.put("thisNode", thisNode);
		return parameters;
	}

	@Override
	public Parameters childProcess(Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
		Stack<Node> path = (Stack<Node>) parameters.get("path");
		Criteria criteria = (Criteria) parameters.get("criteria");
		ActionX actionx = (ActionX) parameters.get("actionx");
		
		Node child = children.get(i);
		Parameters childParameters = new Parameters(parameters);
		child._forEach(path, childParameters, criteria, actionx);
		return parameters;
	}

	@Override
	public Parameters postNode(Node thisNode, Parameters parameters) {
		return parameters;
	}
}
