package io.github.rhino.criteria;

import java.util.Stack;

import io.github.rhino.Node;

public interface Criteria {
	public boolean exit(Stack<Node> path);
}
