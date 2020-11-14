package io.github.rhino.action;

import java.util.ArrayList;
import java.util.Stack;

import io.github.parameters.Parameters;
import io.github.rhino.Node;

public class PrintPath implements Action {
//	Stack<Node> path;
//	
//	public PrintPath(Stack<Node> path) {
//		this.path = path;
//	}
	
	@Override
	public Parameters startParameters() {
		Parameters parameters = new Parameters();
		return parameters;
	}

	@Override
	public Parameters preNode(Node thisNode, Parameters parameters) {
		Stack<Node> path = (Stack<Node>) parameters.get("path");
		for(int i=0; i<path.size(); i++) {
			if(i>0)
				System.out.print("/");
			System.out.print(path.get(i).getName());
		}
		System.out.println();
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
