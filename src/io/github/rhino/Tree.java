package io.github.rhino;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.action.Action;
import io.github.rhino.action.SearchAction;
import io.github.rhino.action.SerializeAction;
import io.github.rhino.actionx.ActionX;
import io.github.rhino.actionx.PathQueryActionX;
import io.github.rhino.actionx.WriterIndentX;
import io.github.rhino.criteria.Criteria;
import io.github.rhino.criteria.DefaultCriteria;

// 
// new Tree class
// change from Node[] in Tree.java to Nodes
//

public class Tree {
//	public static final int DEFAULTTREESIZE = 100 * 1024;
	Nodes nodes;
	
	public Tree() {
		nodes = new Nodes(10*1024);				// default 10k nodes
	}
	
	public Tree(Nodes nodes) {
		this.nodes = nodes;
	}
	
	public void initRoot(String string) {
		Node node = new Node(null, 0, string);
//		this.nodes.set(0, node);
		this.nodes.add(node);
	}

	public void save(String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(filename));
		for(int i=0; i<nodes.size(); i++) {
			Node node = nodes.get(i);
			String output;
			if( node.parent != null ) {
				output = String.format("%d %d,", node.parent.getId(), node.getId());
			} else {
				output = String.format("%d %d,", 0, node.getId());
			}
			fos.write(output.getBytes());
		}
		fos.write("\n".getBytes());
		for(int i=0; i<nodes.size(); i++) {
			fos.write((i + ":" + nodes.get(i).getName() + "\n").getBytes());
		}
		fos.close();
	}


	public void load(String filename) {
		int rootId = nodes.size();
		_load(rootId, filename);
	}
		
	public void _load(int rootId, String filename) {
		//		String content = new Scanner(new File(filename)).useDelimiter("\\Z").next();
		int baseSize = nodes.size();
		int mergeNode = 0;
		if( rootId > 0 ) {
			mergeNode = 1;
		}
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int i=0;
			while ((line = br.readLine()) != null) {
				String serializedTree;
				if( i== 0 ) {								
														// load tree structure
					String[] relationship = line.split(",");
//					nodes = new Nodes(relationship.length);
					for(int j=0; j<relationship.length; j++) {
						Node nullNode = new Node(null, 0, "");
						nodes.add(nullNode);
					}
					for(String pair : relationship ) {
						String[] pands = pair.split(" ");
						int parentId = Integer.parseInt(pands[0]) + baseSize - mergeNode;
						if( parentId == baseSize - 1 ) {
							parentId = rootId;
						}
						int childNodeId = Integer.parseInt(pands[1]) + baseSize - mergeNode;
						Node parentNode = nodes.get(parentId);
						Node childNode = new Node(nodes.get(parentId), childNodeId, "");
						nodes.set(childNodeId, childNode);
					}

				} else if ( i > 0) {						
															// load node names
					String words[] = line.split(":");
					int index = Integer.parseInt(words[0]) + baseSize - mergeNode;
					String nodeName = words[1];
//					nodes[index].setName(nodeName);
					nodes.get(index).setName(nodeName);
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
		return nodes.get(0);
	}
	
	public void printr() {
		getRoot().printr();
	}

	public void saveAsText(String filename) throws IOException {
		FileWriter myWriter = new FileWriter(filename);
		WriterIndentX writerIndentX = new WriterIndentX(myWriter);
		this.forEach(p -> p.size()>99 , writerIndentX);
		myWriter.close();
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
		Path path = new Path();
		parameters.put("path", path);
		parameters.put("thisNode", this);
		parameters.put("criteria", criteria);
		parameters.put("actionX", actionX);
		getRoot()._forEach(path, parameters, criteria, actionX);
		return parameters;
	}

	public Node pathQuery(String queryPath) {
//		Parameters parameters = tree.forEach(new DefaultCriteria(), new PathQueryActionX(queryPath));
		PathQueryActionX pathQueryActionX = new PathQueryActionX(queryPath);
		Parameters parameters = pathQueryActionX.startParameters();
		DefaultCriteria criteria = new DefaultCriteria();
		Path path = new Path();
		parameters.put("path", path);
		parameters.put("thisNode", this);
		parameters.put("criteria", criteria);
		parameters.put("actionX", pathQueryActionX);
		getRoot()._forEach(path, parameters, criteria, pathQueryActionX);
		Node targetNode = (Node) parameters.shared().get("targetNode");
		return targetNode;
	}

	public ArrayList<Path> search(String targetString) {
		return SearchAction.treeSearch(this, targetString);
	}

	public void printr(Criteria criteria) {
		getRoot().printr(criteria);
	}

	public String serialize() {
		SerializeAction serializeAction = new SerializeAction();
		Criteria criteria = new DefaultCriteria();
		
		Parameters parameters = serializeAction.startParameters();
		Path path = new Path();
		parameters.put("path", path);
		parameters.put("thisNode", this);
		getRoot()._forEach(path, parameters, criteria, serializeAction);
		StringBuilder serializeSB = (StringBuilder) parameters.shared().get("serializeSB");
		return serializeSB.toString();
	}
}
