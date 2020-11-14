package io.github.rhino;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

import io.github.parameters.Parameters;
import io.github.rhino.action.Action;
import io.github.rhino.actionx.ActionX;
import io.github.rhino.criteria.Criteria;
import io.github.rhino.criteria.DefaultCriteria;

public class Node {
	private int id;
	private String name;
	Node parent;
	private ArrayList<Node> children;

	public Node(Node parent, int id, String name) {
		this.setId(id);
		this.setName(name);
		this.parent = parent;

		if(parent!=null ) {
			if( parent.getChildren() == null ) {
				parent.setChildren(new ArrayList<Node>());
			}
			parent.getChildren().add(this);
		}
	}

	public Node(Node node) {
		this.id = node.id;
		this.name = node.name;
		this.parent = node.parent;
		this.children = node.children;
	}
	
	public void setName(String nodeName) {
		this.name = nodeName;
	}

	public void printr() {
		this._printr("", new Stack<Node>(), new DefaultCriteria());
	}

	public void printr(Criteria criteria) {
		this._printr("", new Stack<Node>(), criteria);
	}

	protected final int LINELIMIT=80;
	public void printNode(String indent, String prompt, String text, Boolean isLastNode) {
		String mIndent=indent;
		if ( indent.length() > 2 ) {
			mIndent = indent.substring(0, indent.length()-1) + "+";
		}
		if(text.length()>LINELIMIT ) {
			String mIndent2 = indent;
			if(isLastNode && indent.length() > 0) {
				mIndent2 = indent.substring(0, indent.length()-1) + "|";
			}
			System.out.println(mIndent2 + blank(prompt) + "+" + repeat("-", 83) + "+");
			for(int i=0; i<text.length(); i+=LINELIMIT ) {
				int end = Math.min(i+LINELIMIT, text.length());
				String textSeg = text.substring(i, end);
				if( i==0 ) {
					System.out.println(mIndent + prompt + ": " + fillBlank(textSeg) + "  |" );
				} else {
					System.out.println(indent +  blank(prompt) + "| " + fillBlank(textSeg) + "  |");
				} 
			}
			System.out.println(indent + blank(prompt) + "+" + repeat("-", 83) + "+");
		} else {
			System.out.println(mIndent + prompt + " " + text );
		}
	}
	

	private String repeat(String letter, int times) {
		return new String(new char[times]).replace("\0", letter);
	}
	
	private String blank(String prompt) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<prompt.length(); i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	private String fillBlank(String textSeg) {
		StringBuilder sb = new StringBuilder();
		sb.append(textSeg);
		for(int i=textSeg.length(); i<80; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	//
	// \\-- display style
	//
	public void _printr(String indent) {
		System.out.println(indent + "\\--" + this.getId());

		ArrayList<Node> children = this.getChildren();
		if(children!=null) {
			for(int i=0; i<children.size(); i++) {
				Node child = children.get(i);
				if( i < children.size() - 1 ) {
					child._printr(indent + "   |");
				} else {
					child._printr(indent + "    ");
				}
			}
		}
	}

	//
	//   +--- display style
	//
	public void _printr(String indent, Stack<Node> path, Criteria criteria) {
		if(path.contains(this) || criteria.exit(path))
			return;

		path.push(this);
		String indent2=indent;
		if ( indent.length() > 2 ) {
			indent2 = indent.substring(1, indent.length()-1) + "+";
		}
		System.out.println(indent2 + "---" + this.getId() + " " + this.getName());

		ArrayList<Node> children = this.getChildren();
		if(children!=null) {
			for(int i=0; i<children.size(); i++) {
				Node child = children.get(i);
				String childIndent;
				if( i < children.size() - 1 ) {
					childIndent = indent + "   |";
				} else {
					childIndent = indent + "    ";
				}
				child._printr(childIndent, path, criteria);
			}
		}
		path.pop();
	}

	public void forEach(Criteria criteria, Action action) {
		Parameters parameters = action.startParameters();
		Stack<Node> path = new Stack<Node>();
		parameters.put("path", path);
		parameters.put("thisNode", this);
		_forEach(path, parameters, criteria, action);
	}

	public void _forEach(Stack<Node> path, Parameters parameters, Criteria criteria, Action action) {
		if(path.contains(this) || criteria.exit(path))
			return;

		path.push(this);
		action.preNode(this, parameters);

		ArrayList<Node> children = this.getChildren();
		if(children!=null) {
			for(int i=0; i<children.size(); i++) {
				Node child = children.get(i);
				Parameters childParameters = action.preChild(this, i, children, parameters);
				child._forEach(path, childParameters, criteria, action);
				action.postChild(this, i, children, parameters, childParameters);
			}
		}

		action.postNode(this, parameters);
		path.pop();
	}

	public void forEach(Criteria criteria, ActionX actionX) {
		Parameters parameters = actionX.startParameters();
		Stack<Node> path = new Stack<Node>();
		parameters.put("path", path);
		parameters.put("criteria", criteria);
		parameters.put("actionX", actionX);
		parameters.put("thisNode", this);
		_forEach(path, parameters, criteria, actionX);
	}

	public void _forEach(Stack<Node> path, Parameters parameters, Criteria criteria, ActionX actionX) {
		if(path.contains(this) || criteria.exit(path))
			return;

		path.push(this);
		actionX.preNode(this, parameters);

		ArrayList<Node> children = this.getChildren();
		if(children!=null) {
			for(int i=0; i<children.size(); i++) {
				Node child = children.get(i);
				// replaced following code
				//				Parameters childParameters = action.preChild(this, i, children, parameters);
				//				child._forEach(path, childParameters, criteria, action);
				//				action.postChild(this, i, children, parameters, childParameters);
				// whith below
				actionX.childProcess(this, i, children, parameters);
			}
		}

		actionX.postNode(this, parameters);
		path.pop();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		if( parent!=null )
			return "Node parent(" + parent.id + ") id=" +id + " name="+name;
		else 
			return "Node parent(null) id=" +id + " name="+name;
	}

	public boolean isSingleChild() {
		if(this.children!=null && this.children.size()==1)
			return true;
		return false;
	}

	public void add(Node child) {
		if( this.children == null ) {
			this.children = new ArrayList<Node>();
		}
		this.children.add(child);
	}
}
