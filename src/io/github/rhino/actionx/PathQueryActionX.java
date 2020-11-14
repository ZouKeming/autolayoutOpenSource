package io.github.rhino.actionx;

import java.util.ArrayList;
import java.util.Stack;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.Path;
import io.github.rhino.Tree;
import io.github.rhino.action.Action;
import io.github.rhino.criteria.Criteria;
import io.github.rhino.criteria.DefaultCriteria;

public class PathQueryActionX implements ActionX {
	
	private String[] queryPath;

	public PathQueryActionX(String queryPath) {
		this.queryPath = queryPath.split("/");
	}
	
	@SuppressWarnings("unchecked")
	public static Node query(Tree tree, String queryPath) {
		Parameters parameters = tree.forEach(new DefaultCriteria(), new PathQueryActionX(queryPath));
		Node targetNode = (Node) parameters.shared().get("targetNode");
		return targetNode;
	}
	
	@Override
	public Parameters startParameters() {
		Parameters parameters = new Parameters();
		ArrayList<Path> paths = new ArrayList<Path>();
//		parameters.shared().put("paths", paths);
		return parameters;
	}

	@Override
	public Parameters preNode(Node thisNode, Parameters parameters) {
		return parameters;
	}

	@Override
	public Parameters childProcess(Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
		int level = parameters.getInteger("level");
		
		Node childNode = children.get(i);
		String queryNode = this.queryPath[level+1];
		if( childNode.getName().equals(queryNode) ) {
			Parameters childParameters = new Parameters(parameters);
			Path path = (Path) parameters.get("path");
			Criteria criteria = (Criteria) parameters.get("criteria");
			ActionX actionX = (ActionX) parameters.get("actionX");
			childNode._forEach(path, childParameters, criteria, actionX);
			parameters.setShared(childParameters.shared());
			Node targetNode = (Node) parameters.shared().get("targetNode");
			if(targetNode==null)
				parameters.shared().put("targetNode", childNode);
		}
		return parameters;
	}

	@Override
	public Parameters postNode(Node thisNode, Parameters parameters) {
		return parameters;
	}

}
