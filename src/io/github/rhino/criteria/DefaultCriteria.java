package io.github.rhino.criteria;

import java.util.Stack;

import io.github.rhino.Node;

public class DefaultCriteria implements Criteria {

	@Override
	public boolean exit(Stack<Node> path) {
		return false;
	}

}
