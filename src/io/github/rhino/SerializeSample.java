package io.github.rhino;

import java.io.IOException;
import java.util.ArrayList;

public class SerializeSample extends ArrayTree {

	public static void main(String[] argv) throws NumberFormatException, IOException {
		// load GraphSchema
		Tree tree = new Tree();
		tree.load("GraphSchema");
		tree.printr();
		
		System.out.println("==========================");
		String serializeString = tree.serialize();
		System.out.println(serializeString);
	}
}
