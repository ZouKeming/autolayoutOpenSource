package io.github.rhino.action;

import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.Node;

public class DefaultAction implements Action {
	@Override
	public Parameters startParameters() {
		Parameters parameters = new Parameters();
		return parameters;
	}

	@Override
	public Parameters preNode(Node thisNode, Parameters parameters) {
		return parameters;
	}

	@Override
	public Parameters preChild(Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
		Parameters childParameters = new Parameters(parameters);
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
