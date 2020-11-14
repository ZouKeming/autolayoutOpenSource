package io.github.rhino.action;

import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.Path;
import io.github.rhino.Tree;
import io.github.rhino.criteria.DefaultCriteria;

public class SearchAction implements Action {
	
	private String pattern;

	public SearchAction(String pattern) {
		this.pattern = pattern;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Path> treeSearch(Tree tree, String pattern) {
		Parameters parameters = tree.forEach(new DefaultCriteria(), new SearchAction(pattern));
		ArrayList<Path> paths = (ArrayList<Path>) parameters.shared().get("paths");
		return paths;
	}
	
	@Override
	public Parameters startParameters() {
		Parameters parameters = new Parameters();
		ArrayList<Path> paths = new ArrayList<Path>();
		parameters.shared().put("paths", paths);
		return parameters;
	}

	@Override
	public Parameters preNode(Node thisNode, Parameters parameters) {
		Path path = (Path) parameters.get("path");
		ArrayList<Path> paths = (ArrayList<Path>) parameters.shared().get("paths");
		
		if( thisNode.getName().matches(pattern) ) {
			paths.add((Path) path.clone());
		}
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
