package io.github.rhino;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import io.github.parameters.Parameters;
import io.github.rhino.action.Action;
import io.github.rhino.actionx.ActionX;
import io.github.rhino.criteria.Criteria;

public class ArrayTree {
	private static final int DEFAULTTREESIZE = 100 * 1024;
	Node[] nodes;
	
	public ArrayTree() {
		this(DEFAULTTREESIZE);
	}
	
	public ArrayTree(int size) {
		nodes = new Node[size];
	}
	
	public ArrayTree(Node[] nodes) {
		this.nodes = nodes;
	}
	
	public void initRoot(String string) {
		this.nodes[0] = new Node(null, 0, string);
	}

	public void save(String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(filename));
		for(int i=0; i<nodes.length; i++) {
			Node node = nodes[i];
			String output;
			if( node.parent != null ) {
				output = String.format("%d %d,", node.parent.getId(), node.getId());
			} else {
				output = String.format("%d %d,", 0, node.getId());
			}
			fos.write(output.getBytes());
		}
		fos.write("\n".getBytes());
		for(int i=0; i<nodes.length; i++) {
			fos.write((i + ":" + nodes[i].getName() + "\n").getBytes());
		}
		fos.close();
	}


	public void load(String filename) {
		//		String content = new Scanner(new File(filename)).useDelimiter("\\Z").next();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int i=0;
			while ((line = br.readLine()) != null) {
				if( i== 0 ) {								
															// load tree structure
					for(String pair : line.split(",")) {
						String[] pands = pair.split(" ");
						int parent = Integer.parseInt(pands[0]);
						int thisNode = Integer.parseInt(pands[1]);
						Node node = new Node(nodes[parent], thisNode, "");
						nodes[thisNode] =  node;
					}
				} else if ( i > 0) {						
															// load node names
					String words[] = line.split(":");
					int index = Integer.parseInt(words[0]);
					String nodeName = words[1];
					nodes[index].setName(nodeName);
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Node getRoot() {
		return nodes[0];
	}
	
	public void printr() {
		getRoot().printr();
	}
	
	public Parameters forEach(Criteria criteria, Action action) {
		Parameters parameters = action.startParameters();
		Path path = new Path();
		parameters.put("path", path);
		parameters.put("thisNode", this);
		getRoot()._forEach(path, parameters, criteria, action);
		return parameters;
	}
	
	public Parameters forEach(Criteria criteria, ActionX actionX) {
		Parameters parameters = actionX.startParameters();
		Stack<Node> path = new Stack<Node>();
		parameters.put("path", path);
		parameters.put("thisNode", this);
		parameters.put("criteria", criteria);
		parameters.put("actionX", actionX);
		getRoot()._forEach(path, parameters, criteria, actionX);
		return parameters;
	}
}
