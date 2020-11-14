package io.github.rhino;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import io.github.rhino.actionx.PrintIndentX;
import io.github.rhino.actionx.WriterIndentX;

public class TextTree extends ArrayTree {

	public static ArrayTree loadATree(String filename) throws FileNotFoundException {
		Nodes nodes = _loadText(filename);
		
		Node[] nodeArray = new Node[nodes.size()]; 
		nodes.toArray(nodeArray);
		ArrayTree retval = new ArrayTree(nodeArray);
		return retval;
	}
		
	public static Tree loadTree(String filename) throws FileNotFoundException {
		Nodes nodes = _loadText(filename);
		
		Tree retval = new Tree(nodes);
		return retval;
	}
		
	public static Nodes _loadText(String filename) throws FileNotFoundException {
		boolean DEBUG = false;
		File myObj = new File(filename);
		Scanner myReader = new Scanner(myObj);
		String currentIndent="";
		Stack<String> indentStack = new Stack<String>();
		indentStack.push(currentIndent);
		int lineNumber = 0;
		Node root=null;
		Nodes nodes = new Nodes();
		Path path = new Path();
		Node prevNode = null;
		String line = null;
		while (myReader.hasNextLine()) {
			line = myReader.nextLine();
			String indent = calcIndent(line);
			if( DEBUG )
				System.out.println(": " + lineNumber);
			if(indent.length() > currentIndent.length()) {
				currentIndent = indent;
				indentStack.push(currentIndent);
				if ( DEBUG )
					System.out.println(indent + "push()");

				path.push(prevNode);
				Node childNode = addChild(nodes, path, lineNumber, line);
				prevNode = childNode;
			} else if ( indent.length() == currentIndent.length() ) {
				if ( DEBUG )
					System.out.println(indent + "addChild()");
				if(root==null) {
					root = new Node(null, lineNumber, stripe(line));
					nodes.add(root);
					prevNode = root;
				} else {
					Node childNode = addChild(nodes, path, lineNumber, line);
					prevNode = childNode;
				}
			} else {
				// in case of indent.length() < currentIndent;
				currentIndent = indentStack.pop();
				if ( DEBUG )
					System.out.println(currentIndent + "popup()");
				path.pop();
				if ( currentIndent.length() > indent.length() ) {
					Node top=null;
					while(currentIndent.length()>indent.length()) {
						currentIndent = indentStack.pop();
						top = path.pop();
						if ( DEBUG )
							System.out.println(currentIndent + "popup()");
					}
					indentStack.push(currentIndent);
					path.push(top);
				}

				Node childNode = addChild(nodes, path, lineNumber, line);
				path.push(childNode);
				if( currentIndent.length() != indent.length() ) {
					System.out.println("Error： indent does not match previous lines");
				}
			}
			lineNumber++;
			if ( DEBUG )
				System.out.println(line);
		}
		myReader.close();

		return nodes;
	}

	private static String stripe(String line) {
		return  line.replaceAll("^[ \t]+|[ \t]+$", "");
	}

	private static Node addChild(ArrayList<Node> nodes, Path path, int lineNumber, String line) {
		Node parentNode = getParentNodeFromPath(path);
		Node childNode = new Node(parentNode, lineNumber, line.replaceAll("^[ \t]+|[ \t]+$", ""));
		nodes.add(childNode);
		return childNode;
	}

	private static Node getParentNodeFromPath(Path path) {
		if( path.size() == 0) {
			return null;
		} else if ( path.size() == 1 ) {
			return path.get(0);
		} else {
			// in case of path.size() > 1
			Node parentNode = path.get(path.size() - 1);
			return parentNode;
		}
	}

	private static String calcIndent(String line) {
		StringBuilder indent = new StringBuilder();
		for(int i=0; i<line.length(); i++) {
			char ch = line.charAt(i);
			if( ch=='\t'|| ch==' ') {
				indent.append(ch);
			} else {
				break;
			}
		}
		return indent.toString();
	}

	public void saveAsText(String filename) throws IOException {
		FileWriter myWriter = new FileWriter(filename);
		WriterIndentX writerIndentX = new WriterIndentX(myWriter);
		this.forEach(p -> p.size()>99 , writerIndentX);
		myWriter.close();
	}

	public static void main(String[] argv) throws NumberFormatException, IOException {
			// load GraphSchema
			TextTree textTree = new TextTree();
			textTree.load("GraphSchema");
			textTree.printr();
	
			System.out.println("==========================");
			PrintIndentX printIndentX = new PrintIndentX();
			textTree.forEach(p -> p.size()>99 , printIndentX);
	
			String filename = "tree.txt";
	//		System.out.println("==========================");
	//		textTree.saveAsText(filename);
			
			Tree result = TextTree.loadTree(filename);
			result.printr();
		}
}
