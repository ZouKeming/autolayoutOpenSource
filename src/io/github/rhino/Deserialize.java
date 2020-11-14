package io.github.rhino;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Deserialize {

	public static void main(String[] argv) throws NumberFormatException, IOException {
		// load GraphSchema
		Tree tree = new Tree();
		tree.load("GraphSchema");
		tree.printr();

		System.out.println("==========================");
		String serializeString = tree.serialize();
		System.out.println(serializeString);

		String serializeFileName = "Serialized.txt";
		File output = new File(serializeFileName);
		FileWriter writer = new FileWriter(output);
		writer.write(serializeString);
		writer.flush();
		writer.close();

//		load(serializeFileName);
		
		System.out.println("=======================");
		deserialize(serializeString);
	}

	private static void deserialize(String serializeString) {
		_deserialize(null, serializeString, 0, "");
		root.printr();
	}
	
	static int nodeId=0;
	static Node root;
	private static int _deserialize(Node parent, String serializeString, int start, String indent) {
		StringBuilder word = new StringBuilder();
		int i=start;
//		parent = "";
		for(; i<serializeString.length(); i++) {
			char ch = serializeString.charAt(i);
			if( ch == '{' ) {
				System.out.println(indent + "lb " + parent + "\t" + word.toString());
				Node child = new Node(parent, nodeId++, word.toString());
				if( nodeId==1 ) {
					root = child;
				}
				i = _deserialize(child, serializeString, i+1, indent + "\t") ;
				word = new StringBuilder();
//				word.append(ch);
			} else if ( ch == ',' ) {
				if( word.length() > 0 ) {
					System.out.println(indent + "cm " + parent + "\t" + word.toString());
					Node child = new Node(parent, nodeId++, word.toString());
				}
				word = new StringBuilder();
//				word.append(ch);
			} else if ( ch == '}' ) {
				if( word.length() > 0 ) {
					System.out.println(indent + "rb " + parent + "\t" + word.toString());
					Node child = new Node(parent, nodeId++, word.toString());
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

	private static void load(String serializeFileName) throws IOException {
		FileReader fr = new FileReader(serializeFileName); 

		int i; 
		while ( (i=fr.read()) != -1) {
			System.out.print((char) i);
			if( i == '\\' ) {
				// skip this char
			} else if ( i=='{') {
				System.out.println();
			}
		}
	} 
}
