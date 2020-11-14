package io.github.rhino.miniscript;

import java.io.FileReader;
import java.io.IOException;

import io.github.parameters.Parameters;
import io.github.rhino.Node;

public class Interpreter {

	public static void main(String[] argv) throws NumberFormatException, IOException {
		String script = load(Config.scriptName);

		System.out.println("=======================");
		deserialize(script);
	}

	private static void deserialize(String serializeString) {
		_deserialize(null, serializeString, 0, "");
		root.printr();
	}

	static int nodeId=0;
	static Node root;
	private static int _deserialize(Node parent, String serializeString, int start, String indent) {
		boolean DEBUG=false;
		StringBuilder word = new StringBuilder();
		int i=start;
		for(; i<serializeString.length(); i++) {
			char ch = serializeString.charAt(i);
			if( ch == '{' ) {
				if( DEBUG ) {
					System.out.println(indent + "lb " + parent + "\t" + word.toString());
				}
				Node child = statementAction(parent, nodeId++, word.toString());
				if( nodeId==1 ) {
					root = child;
				}
				i = _deserialize(child, serializeString, i+1, indent + "\t") ;
				word = new StringBuilder();
				//				word.append(ch);
			} else if ( ch == ',' ) {
				if( word.length() > 0 ) {
					if( DEBUG ) {
						System.out.println(indent + "cm " + parent + "\t" + word.toString());
					}
					statementAction(parent, nodeId++, word.toString());
				}
				word = new StringBuilder();
				//				word.append(ch);
			} else if ( ch == '}' ) {
				if( word.length() > 0 ) {
					if ( DEBUG ) {
						System.out.println(indent + "rb " + parent + "\t" + word.toString());
					}
					statementAction(parent, nodeId++, word.toString());
				}
				word = new StringBuilder();
				word.append(ch);
				return i;
			} else {
				word.append(ch);
			}
		}
		return i;
	}

	private static Node statementAction(Node parent, int nodeId, String statement) {
		Parameters parameters = new Parameters();
		_statementAction(0, parent, nodeId, statement, parameters);
		Node childNode = (Node) parameters.get("childNode");
		return childNode;
	}

	private static int _statementAction(int start, Node parent, int nodeId, String statement, Parameters parameters) {
		int i=start;
		StringBuilder word = new StringBuilder();
		for(; i<statement.length(); i++) {
			char ch = statement.charAt(i);
			if( ch == '\'' ) {
				i = quotedString(i+1, parent, nodeId, statement, parameters);
				String quotedString = parameters.getString("quotedString");
				Node childNode = new Node(parent, nodeId, quotedString);
				parameters.put("childNode", childNode);
			} else if ( ch=='\n') {
			} else if ( ch=='\t') {
			} else {
				if('0' <= ch && ch <= '9' ) {
					Parameters childParameters = new Parameters(parameters);
					i = constantAction(i, parent, nodeId, statement, childParameters);
					String constant = childParameters.getString("constant");
					String constantString = "constant : " + constant ;
					Node childNode = new Node(parent, nodeId, constantString);
					//					System.out.println("constant : " + constant);
				} else {
					System.out.println("not constant here");

					Parameters childParameters = new Parameters(parameters);
					i = expressParse(i, parent, nodeId, statement, childParameters);
					Node expressTree = (Node) childParameters.get("expressTree");
					Node result = expressAction(expressTree);
					
					parent.add(result);
//					parent.getChildren().add(result);
				}
			}
			word.append(ch);
		}
		return i;
	}

	private static Node expressAction(Node expressTree) {
		// copy varible data to here
		return null;
	}

	private static int expressParse(int start, Node parent, int nodeId, String statement, Parameters parameters) {
		int i=start;
		StringBuilder word = new StringBuilder();
		for(; i<statement.length(); i++) {
			char ch = statement.charAt(i);
			if( ('a' <= ch && ch <= 'z') 
					|| 'A' <= ch && ch <= 'Z' ) {
				Parameters childParameters = new Parameters(parameters);
				i = variableCheck(i+1, parent, nodeId, statement, childParameters);
				String variableNode = childParameters.getString("varibleNode");
			} else if ( ch=='\n') {
			} else if ( ch=='\t') {
			} else {
				if('0' <= ch && ch <= '9' ) {
					Parameters childParameters = new Parameters(parameters);
					i = constantAction(i, parent, nodeId, statement, childParameters);
					String constant = childParameters.getString("constant");
					String constantString = "constant : " + constant ;
					Node childNode = new Node(parent, nodeId, constantString);
					//					System.out.println("constant : " + constant);
				}
			}
			word.append(ch);
		}
		
		return i;
	}

	private static int variableCheck(int start, Node parent, int nodeId, String statement, Parameters parameters) {
		int i=start;
		StringBuilder word = new StringBuilder();
		for(; i<statement.length(); i++) {
			char ch = statement.charAt(i);
			if( ('a' <= ch && ch <= 'z') 
					|| 'A' <= ch && ch <= 'Z' 
					|| '0' <= ch && ch <= '9' 
					|| ch == '_' ) {
				word.append(ch);
			} else {
				if( word.length() > 0 ) {
					String variableName = word.toString();
					parameters.put("variableName", variableName);
					return i;
				}
			}
		}
		return i;
	}

//	private static void variableAction(Node parent, int nodeId, String variableName, Parameters parameters) {
//		// copy variable to here
//		System.out.println("copy varible \"" + variableName + "\"");
//
//		Parameters childParameters = new Parameters(parameters);
//		i = expressParse(i, parent, nodeId, statement, childParameters);
//		Node result = (Node) childParameters.get("result");
//		parent.getChildren().add(result);
//	}

	private static int constantAction(int start, Node parent, int nodeId, String statement, Parameters parameters) {
		int i=start;
		StringBuilder word = new StringBuilder();
		for(; i<statement.length(); i++) {
			char ch = statement.charAt(i);
			if( '0' <= ch && ch <= '9' ) {
				word.append(ch);
			}
		}
		parameters.put("constant", word.toString());
		return i;
	}

	private static int quotedString(int start, Node parent, int nodeId2, String statement, Parameters parameters) {
		int i=start;
		StringBuilder word = new StringBuilder();
		for(; i<statement.length(); i++) {
			char ch = statement.charAt(i);
			if( ch == '\\' ) {
				// do nothint skip
			} else if ( ch == '\''){
				parameters.put("quotedString", word.toString());
			}
			word.append(ch);
		}
		return i;
	}

	private static String load(String serializeFileName) throws IOException {
		FileReader fr = new FileReader(serializeFileName); 

		StringBuilder sb = new StringBuilder();
		int i; 
		while ( (i=fr.read()) != -1) {
			sb.append((char) i);
		}
		return sb.toString();
	} 
}
