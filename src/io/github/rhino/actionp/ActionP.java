package io.github.rhino.actionp;

import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.Path;

public interface ActionP {
	public Parameters startParameters();
	public Parameters preNode(Path path, Node thisNode, Parameters parameters);
	public Parameters preChild(Path path, Node thisNode, int i, ArrayList<Node> children, Parameters parameters);
	public Parameters postChild(Path path, Node thisNode, int i, ArrayList<Node> children, Parameters parameters, Parameters childParameters);
	public Parameters postNode(Path path, Node thisNode, Parameters parameters);
}
