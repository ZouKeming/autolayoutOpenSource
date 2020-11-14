package io.github.rhino.action;

import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.Node;

public interface Action {
	public Parameters startParameters();
	public Parameters preNode(Node thisNode, Parameters parameters);
	public Parameters preChild(Node thisNode, int i, ArrayList<Node> children, Parameters parameters);
	public Parameters postChild(Node thisNode, int i, ArrayList<Node> children, Parameters parameters, Parameters childParameters);
	public Parameters postNode(Node thisNode, Parameters parameters);
}
