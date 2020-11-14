package io.github.rhino.miniscript;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.Path;
import io.github.rhino.Tree;
import io.github.rhino.action.ScriptAssistantAction;
import io.github.rhino.criteria.Criteria;
import io.github.rhino.criteria.DefaultCriteria;

public class ScriptGenerator {

	public static void main(String[] argv) throws NumberFormatException, IOException {
		// load GraphSchema
		Tree tree = new Tree();
		tree.load("GraphSchema");
		tree.printr();
		
		System.out.println("==========================");
		String serializeString = scriptGenerate(tree);
		System.out.println(serializeString);
		
		File output = new File(Config.scriptName);
		FileWriter writer = new FileWriter(output);
		writer.write(serializeString);
		writer.flush();
		writer.close();
	}
	
	public static String scriptGenerate(Tree tree) {
		ScriptAssistantAction scriptAssistantAction = new ScriptAssistantAction();
		Criteria criteria = new DefaultCriteria();
		
		Parameters parameters = scriptAssistantAction.startParameters();
		Node thisNode = tree.getRoot();
		Path path = new Path();
		parameters.put("path", path);
		parameters.put("thisNode", thisNode);
		thisNode._forEach(path, parameters, criteria, scriptAssistantAction);
		StringBuilder serializeSB = (StringBuilder) parameters.shared().get("serializeSB");
		return serializeSB.toString();
	}

}
