package io.github.rhino.action;

import java.util.ArrayList;
import java.util.Stack;

import io.github.parameters.Parameters;
import io.github.rhino.Node;

public class PrintIndent implements Action {
//	Path path;
//	
//	public PrintPath(Path path) {
//		this.path = path;
//	}
	
	@Override
	public Parameters startParameters() {
		Parameters parameters = new Parameters();
		parameters.put("indent", "");
		return parameters;
	}

	@Override
	public Parameters preNode(Node thisNode, Parameters parameters) {
		String indent = parameters.getString("indent");
		System.out.println(indent + thisNode.getName());
		return parameters;
	}

	@Override
	public Parameters preChild(Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
		String indent = parameters.getString("indent");
		Parameters childParameters = new Parameters(parameters);
		childParameters.put("indent",  indent + "\t");
		return childParameters;
	}

	@Override
	public Parameters postChild(Node thisNode, int i, ArrayList<Node> children, Parameters parameters, Parameters childParameters) {
		return parameters;
	}

	@Override
	public Parameters postNode(Node thisNode, Parameters parameters) {
		return parameters;
	}
}
