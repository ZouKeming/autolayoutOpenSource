package io.github.rhino.actionx;

import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.Node;

public interface ActionX {
	public Parameters startParameters();
	public Parameters preNode(Node thisNode, Parameters parameters);
	public Parameters childProcess(Node thisNode, int i, ArrayList<Node> children, Parameters parameters);
	public Parameters postNode(Node thisNode, Parameters parameters);
}
