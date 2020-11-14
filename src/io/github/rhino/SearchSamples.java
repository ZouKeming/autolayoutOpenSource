package io.github.rhino;

import java.io.IOException;
import java.util.ArrayList;

public class SearchSamples extends ArrayTree {

	public static void main(String[] argv) throws NumberFormatException, IOException {
		// load GraphSchema
		Tree tree = new Tree();
		tree.load("GraphSchema");
		tree.printr();
		
		System.out.println("==========================");
		ArrayList<Path> paths =  tree.search("oiirai");
		for(int i=0; i<paths.size(); i++) {
			System.out.println(paths.get(i));
		}
		
		Path queryPath = paths.get(0);
		String queryString = queryPath.toString();
		Node targetNode = tree.pathQuery(queryString);
		System.out.println(targetNode);
	}
}
