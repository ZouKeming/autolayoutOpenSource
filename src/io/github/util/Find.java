package io.github.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Find {

	public static void find(File directory, String namePattern) {
		_find(directory, namePattern, "");
	}

	public static void _find(File node, String namePattern, String indent) {
		if(node.isDirectory()) {
			//			System.out.println(indent + "\\--" + node.getName() + "/");
			File[] subfiles = node.listFiles();
			for(int i=0; i<subfiles.length; i++) {
				File subfile = subfiles[i];
				String childIndent;
				if( i < subfiles.length - 1 )
					childIndent = indent + "   |";
				else 
					childIndent = indent + "    ";
				_find(subfile, namePattern, childIndent);
			}
		} else {
			String filename = node.getName();
			if(filename.matches(".*java$")) {
				//				System.out.println(indent + "\\--" + filename);
				System.out.println(node.getAbsolutePath());
				try (Stream<String> stream = Files.lines(Paths.get(node.getAbsolutePath()))) {
					stream.forEach(System.out::println);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else { 
				//				System.out.println(indent + "\\--" + filename);
			}
		}
	}

	public static void main(String[] argv) {
		find(new File("."), "");
	}

}
