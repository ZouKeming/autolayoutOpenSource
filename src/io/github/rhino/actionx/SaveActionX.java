package io.github.rhino.actionx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.criteria.Criteria;

public class SaveActionX implements ActionX {
	File saveFile;
	FileOutputStream fos;
	
	public SaveActionX(String filename) {
		saveFile = new File(filename);
		try {
			fos = new FileOutputStream(new File("GraphSchema"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Parameters startParameters() {
		Parameters parameters = new Parameters();
		return parameters;
	}

	@Override
	public Parameters preNode(Node thisNode, Parameters parameters) {
		try {
			fos.write((thisNode.getId()+":"+thisNode.getName()+"\n").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return parameters;
	}

	@Override
	public Parameters childProcess(Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
		Stack<Node> path = (Stack<Node>) parameters.get("path");
		Criteria criteria = (Criteria) parameters.get("criteria");
		ActionX actionx = (ActionX) parameters.get("actionx");
		
		Node child = children.get(i);
		Parameters childParameters = new Parameters(parameters);
		child._forEach(path, childParameters, criteria, actionx);
		return parameters;
	}

	@Override
	public Parameters postNode(Node thisNode, Parameters parameters) {
		return parameters;
	}
}
