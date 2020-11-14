package io.github.rhino.actionx;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.criteria.Criteria;

public class WriterIndentX implements ActionX {
	FileWriter fileWriter;

	public WriterIndentX(FileWriter fileWriter) {
		super();
		this.fileWriter = fileWriter;
	}

	@Override
	public Parameters startParameters() {
		Parameters parameters = new Parameters();
		parameters.put("indent", "");
		return parameters;
	}

	@Override
	public Parameters preNode(Node thisNode, Parameters parameters) {
		String indent = parameters.getString("indent");
		try {
			fileWriter.write(indent + thisNode.getName() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parameters;
	}


	@Override
	public Parameters childProcess(Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
		String indent = parameters.getString("indent");
		@SuppressWarnings("unchecked")
		Stack<Node> path = (Stack<Node>) parameters.get("path");
		Criteria criteria = (Criteria) parameters.get("criteria");
		
		Node child = children.get(i);
		Parameters childParameters = new Parameters(parameters);
		childParameters.put("indent",  indent + "\t");
		child._forEach(path, childParameters, criteria, this);
		return parameters;
	}

	@Override
	public Parameters postNode(Node thisNode, Parameters parameters) {
		return parameters;
	}
}
