package io.github.rhino;

import java.io.IOException;

import io.github.rhino.action.PrintAction;
import io.github.rhino.action.PrintAction2;
import io.github.rhino.action.PrintIndent;
import io.github.rhino.action.PrintPath;
import io.github.rhino.actionx.PrintIndentX;
import io.github.rhino.actionx.PrintPathX;
import io.github.rhino.criteria.DefaultCriteria;

public class TreePrintSamples {

	public static void main(String[] argv) throws NumberFormatException, IOException {
		// load GraphSchema
		Tree tree = new Tree();
		tree.load("GraphSchema");
		tree.printr();
		
		System.out.println("==========================");
		PrintAction printAction = new PrintAction();
		tree.forEach(new DefaultCriteria(), printAction);
		
		System.out.println("==========================");
		PrintAction2 printAction2 = new PrintAction2();
		tree.forEach(p -> p.size()>3 , printAction2);

		System.out.println("==========================");
		PrintPath printPath = new PrintPath();
		tree.forEach(p -> p.size()>4 , printPath);

		System.out.println("==========================");
		PrintIndent printIndent = new PrintIndent();
		tree.forEach(p -> p.size()>4 , printIndent);

		System.out.println("==========================");
		PrintPathX printPathX = new PrintPathX();
		tree.forEach(p -> p.size()>4 , printPathX);
		
		System.out.println("==========================");
		PrintIndentX printIndentX = new PrintIndentX();
		tree.forEach(p -> p.size()>4 , printIndentX);

		// save as text file
		
		
		// load tree from text file
	}
}
