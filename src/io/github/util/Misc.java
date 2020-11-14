package io.github.util;

import java.util.Stack;

import io.github.rhino.Node;

public class Misc {
	final static String DEFUALTDELIMITER = "/";
	
	public static String Path2String(Stack<Node> pathPhrase) {
		return Path2String(pathPhrase, DEFUALTDELIMITER);
	}

	public static String Path2String(Stack<Node> pathPhrase, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<pathPhrase.size(); i++) {
			if( i > 0 ) {
				sb.append(delimiter);
			}
			sb.append(pathPhrase.get(i).getName());
		}
		return sb.toString();
	}

}
