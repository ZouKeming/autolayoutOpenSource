package io.github.util;

import java.io.File;

public class FindJava extends Find {

	public static void main(String[] argv) {
		find(new File("."), "*.java");
	}

}
