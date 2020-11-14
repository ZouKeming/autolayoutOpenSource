package io.github.rhino;

import java.io.IOException;

public class RandomTree extends ArrayTree {	
	enum CONTENT { NUMBER, TEXT }
	String[] nodeNames = new String[nodes.length];

	public static String[] randomNames(int size) {
		String[] retval = new String[size];
		for(int i=0; i<retval.length; i++) {
			retval[i] = RandomVocabulary.randomWord(false);
		}
		
		return retval;
	}
	
	public void testGraphInit() {
		nodes = new Node[33];
		nodeNames = randomNames(nodes.length);
		for(int i=-5; i<7; i++) {
			graphInit(CONTENT.TEXT, (float) Math.pow(2, i));			
			System.out.println("==============================");
			this.printr();		
			System.out.println();
			try {
				this.save("GraphSchema.f"+i);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void graphInit(CONTENT content) {
		int range = (int) (Math.random() * 4);
		graphInit(content, 1<<range);
	}
		
	public void graphInit(CONTENT content, float factor) {
		System.out.println("factor is " + factor);
		
		for(int i=0; i<nodes.length ; i++) {
			int parentIndex = (int) (Math.random() * i * factor);
			parentIndex = parentIndex >= i ? i-1 : parentIndex;
			parentIndex = parentIndex < 0 ? 0 : parentIndex;
			Node parentNode = nodes[parentIndex];
//			System.out.println("parent : " + parentIndex + "\t thisNode ： " + i);
			Node node ;
			if(content.equals(CONTENT.TEXT)) {
				node = new Node(parentNode, i, nodeNames[i]);
			} else {
				node = new Node(parentNode, i, "");
			}
			nodes[i]= node;
		}
	}

	public void test1() {
		this.graphInit(CONTENT.TEXT);
		try {
			this.save("GraphSchema.sparse");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		this.printr();		
	}
	
	public void test2() {
		System.out.println("==============================");
		this.load("GraphSchema");
		this.printr();		
	}

	public static void main(String[] argv) throws IOException {
		//		graphInit(CONTENT.NUMBER);
		RandomTree tree = new RandomTree();
		
		tree.testGraphInit();
	}
}


