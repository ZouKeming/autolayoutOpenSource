package io.github.rhino;

import java.util.Stack;

public class Path extends Stack<Node> {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<this.size(); i++) {
			Node node = this.get(i);
			if( i > 0 ) {
				sb.append("/");
			}
			sb.append(node.getName());
		}
		return sb.toString();
	}

}
