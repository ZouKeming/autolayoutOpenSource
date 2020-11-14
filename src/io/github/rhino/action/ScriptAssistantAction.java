package io.github.rhino.action;

import java.util.ArrayList;

import io.github.parameters.Parameters;
import io.github.rhino.Node;
import io.github.rhino.Path;
import io.github.rhino.Tree;
import io.github.rhino.criteria.DefaultCriteria;

public class ScriptAssistantAction implements Action {
	
	public ScriptAssistantAction() {
	}
	
	@SuppressWarnings("unchecked")
	public static String serialize(Tree tree) {
		Parameters parameters = tree.forEach(new DefaultCriteria(), new ScriptAssistantAction());
		StringBuilder serializeSB = (StringBuilder) parameters.shared().get("serializeSB");
		return serializeSB.toString();
	}
	
	@Override
	public Parameters startParameters() {
		Parameters parameters = new Parameters();
		ArrayList<Path> paths = new ArrayList<Path>();
		parameters.shared().put("paths", paths);
		parameters.shared().put("serializeSB", new StringBuilder());
		parameters.put("indent", "");
		return parameters;
	}

	@Override
	public Parameters preNode(Node thisNode, Parameters parameters) {
		String indent = parameters.getString("indent");
		StringBuilder serializeSB = (StringBuilder) parameters.shared().get("serializeSB");
		serializeSB.append(indent + "'");
		serializeSB.append(thisNode.getName());
		serializeSB.append("'\n");
		ArrayList<Node> children = thisNode.getChildren();
		if(children!=null && children.size() > 0) {
			serializeSB.append(indent + "{\n");
		}
		return parameters;
	}

	@Override
	public Parameters preChild(Node thisNode, int i, ArrayList<Node> children, Parameters parameters) {
		String indent = parameters.getString("indent");
		StringBuilder serializeSB = (StringBuilder) parameters.shared().get("serializeSB");
		if( i > 0 ) {
			serializeSB.append(indent + ",\n");
		}
		Parameters childParameters = new Parameters(parameters);
		childParameters.put("indent", indent + "\t");
		return childParameters;
	}

	@Override
	public Parameters postChild(Node thisNode, int i, ArrayList<Node> children, Parameters parameters, Parameters childParameters) {
		return parameters;
	}

	@Override
	public Parameters postNode(Node thisNode, Parameters parameters) {
		String indent = parameters.getString("indent");
		StringBuilder serializeSB = (StringBuilder) parameters.shared().get("serializeSB");
		ArrayList<Node> children = thisNode.getChildren();
		if(children!=null && children.size() > 0) {
			serializeSB.append(indent + "}\n");
		}
		return parameters;
	}
}
