package io.github.rhino;

import io.github.rhino.RandomTree.CONTENT;
import io.github.rhino.widget.Widget;
import io.github.rhino.widget.WidgetFactory;
import io.github.rhino.window.BasicRule;

public class MagicWorld extends Tree {

	private static final int BASESIZE = 4;
	private static final int VARIETYSIZE = 8;
	private static final int LINESIZELIMIT = 120;
	private String[] nodeNames;

	public MagicWorld(int size) {
		Node parent = new Node(null, 0, "root");
		nodes.add(parent);
		nodeNames = RandomTree.randomNames(size);
	}

	final static int PARAGRAPH=2;
	final static int DOCUMENT=3;
	final static int LIBRARAY=4;
	final static int LIBRARAYCLUSTER=5;
	public static Tree generate() {
		return generate(LIBRARAYCLUSTER);
	}

	public static Tree generate(int level) {
		MagicWorld magic = new MagicWorld(100*1024);
		//		return magic.genParagraph((int)(BASESIZE + Math.random() * VARIETYSIZE));
		magic.initRoot(" ");
		magic._generate(magic.getRoot(), level);
		return magic;
	}

	public Tree generate(Node parent, int level) {
		_generate(parent, level);
		return this;
	}

	public void _generate(Node parent, int level) {
		if( level == 0) 
			return;

		int limit = 3;
		if( level==1 ) {
			limit = randomSentenceSize();
		} else if ( level==2 ) {
			limit = randomParagraphSize();
		}

		for(int i=0; i<limit; i++) {
			Node child;
			if(level==PARAGRAPH) {
				child = new Node(parent, this.nodes.size(), " ");
			} else if( level==DOCUMENT ) {
				child = new Node(parent, this.nodes.size(), "<Document>");
				child = genSentence(child, randomSentenceSize());
			} else if( level==LIBRARAY ) {
				child = new Node(parent, this.nodes.size(), "<Libraray>");
				child = genSentence(child, randomSentenceSize());
			} else if( level==LIBRARAYCLUSTER ) {
				child = new Node(parent, this.nodes.size(), "<LibrarayCluster>");
				child = genSentence(child, randomSentenceSize());
			} else {
				child = genSentence(parent, randomSentenceSize());
			}
			_generate(child, level - 1);
		}
	}

	private int randomSentenceSize() {
		return (int)(BASESIZE + Math.random() * VARIETYSIZE);
	}

	private int randomParagraphSize() {
		return 3 + (int) (Math.random() * 2);
	}

	public Node genSentence(Node parent, int size) {
		Node newNode =  parent;
		for(int i=1; i<size; i++) {
			int targetI = i + parent.getId();
			int newNodeId = this.nodes.size();
			newNode = new Node(newNode, newNodeId, nodeNames[newNodeId]);
			this.nodes.add(newNode); 
		}
		return newNode;
	}

	public static Tree createRandomTree(int levelLimit, int size, float factor) {
		MagicWorld magic = new MagicWorld(size);
		magic.randomTreeFixFactor(levelLimit, size, factor);
		return magic;
	}

	public static Tree createRandomTree(int levelLimit, int size, double anamorphosis) {
		MagicWorld magic = new MagicWorld(size);
		magic.randomTreeAnamophosis(levelLimit, size, anamorphosis);
		return magic;
	}

	interface Factor {
		public float factor(int levelLimit, int level, double anamorphosis);
	}


	private float factor(int levelLimit, int level, double anamorphosis) {
		return (float) Math.pow(anamorphosis, levelLimit-level-Math.round(level/2));
	}

	Factor defaultFactor = new Factor() {
		@Override
		public float factor(int levelLimit, int level, double anamorphosis) {
			return 1.0f;
		}
	};

	Factor anamophosisFactor = new Factor() {
		@Override
		public float factor(int levelLimit, int level, double anamorphosis) {
			double exponment;
			if(levelLimit==1)
				exponment = 1;
			else 
				exponment = levelLimit-level-Math.round(levelLimit/2); 
			return (float) Math.pow(anamorphosis, exponment) ;
		}
	};

	private static final double ANAMOPHOSIS = 256;			// smooth 2, 4, 16, 256 sharp 

	//
	// levelLimit is the max embed level of tree
	// size is the max nodes for once generation. if the tree is a chain, the size means the chain length; 
	// if the tree is an array, the size means the array size;
	//
	// levelLimit ~= height
	// size ~= width
	//
	public Tree randomTreeSmooth(int levelLimit, int size) {
		return randomTreeAnamophosis(levelLimit, size, 2);
	}
		
	public Tree randomTreeSharp(int levelLimit, int size) {
		return randomTreeAnamophosis(levelLimit, size, 256);
	}
		
	public Tree randomTreeAnamophosis(int levelLimit, int size, double anamorphosis) {
		int level = levelLimit;
		_randomTree(CONTENT.TEXT, this.getRoot(), size, anamophosisFactor, anamorphosis, levelLimit, level);
		return this;
	}

	public Tree randomTreeFixFactor(int levelLimit, int size, float factor) {
		int level = levelLimit;
		_randomTree(CONTENT.TEXT, getRoot(), size, defaultFactor, ANAMOPHOSIS, levelLimit, level);
		return this;
	}
	
	// ----------------------------------------------------------------------------------

	public Tree randomTree(int levelLimit, int size, double anamorphosis) {
		int level = levelLimit;
		_randomTree(CONTENT.TEXT, getRoot(), size, anamophosisFactor, anamorphosis, levelLimit, level);
		return this;
	}

	public void _randomTree(CONTENT content, Node parent, int size, Factor factor, double anamorphosis,  int levelLimit, int level) {
		if(level <= 0 )
			return;

		float f = factor.factor(levelLimit, level, anamorphosis);
		if ( false )
			System.out.println("factor is " + f);
		int base = this.nodes.size()-1;
		for(int i=1; i<size; i++) {
			int parentIndex = (int) (Math.random() * i * f);
			parentIndex = parentIndex >= i ? i-1 : parentIndex;
			parentIndex = parentIndex < 0 ? 0 : parentIndex;
			Node parentNode = nodes.get(base + parentIndex);
			if( parentIndex == 0 ) {
				parentNode = parent;
			}
			Node node ;
			int nodeIndex = nodes.size();
			if(content.equals(CONTENT.TEXT)) {
				node = new Node(parentNode, nodeIndex, HashVocabulary.hashWord(nodeIndex));
			} else {
				node = new Node(parentNode, nodeIndex, "");
			}
			nodes.add(node);
		}

//		debug(parent, base, size);
		
		for(int i=0; i<size; i++) {
			Node node = nodes.get(base + i);
			if(node.getChildren()==null) {
				_randomTree(CONTENT.TEXT, node, size, factor, anamorphosis, levelLimit, level-1);
			}
		}
	}

	private void debug(Node parent, int base, int size) {
		Widget widget = WidgetFactory.create(parent, "Documentor");
		widget.forEach(p -> p.size()>99 , new BasicRule());

		for(int i=0; i<size; i++) {
			Node node = nodes.get(base + i);
			System.out.println(node + "\t\t" + node.getChildren());
			if(node.getChildren()==null) {
			}
		}
	}
	
	public static void testGenerate() {
		for(int level=5; level<=LIBRARAYCLUSTER; level++) {
			Tree magicTree = MagicWorld.generate(level);
			Widget widget = WidgetFactory.create(magicTree.getRoot(), "Documentor");
			widget.forEach(p -> p.size()>99 , new BasicRule());
		}
	}

	public static void testRandomWithFixFactor() {
		for(int level=5; level<=8; level++) {
			Tree magicTree = MagicWorld.createRandomTree(level, 5, 1.0f);
			Widget widget = WidgetFactory.create(magicTree.getRoot(), "Documentor");
			widget.forEach(p -> p.size()>99 , new BasicRule());
		}
	}

	public static void testRandomWithFixFactor2() {
		Tree magicTree = MagicWorld.createRandomTree(3, 22, 1f);
		Widget widget = WidgetFactory.create(magicTree.getRoot(), "Documentor");
		widget.forEach(p -> p.size()>99 , new BasicRule());
	}

	public static void testRandomWithAnamophosisFactor() {
		for(int i=1; i<5; i++ ) {
			double anamorphosis = Math.pow(2, i);
			System.out.println(anamorphosis);
			Tree magicTree = MagicWorld.createRandomTree(8, 8, anamorphosis);
			Widget widget = WidgetFactory.create(magicTree.getRoot(), "Documentor");
			widget.forEach(p -> p.size()>99 , new BasicRule());
			
			System.out.println();
		}
		
//		Widget widget2 = WidgetFactory.create(magicTree.getRoot(), "Default");
//		widget2.forEach(p -> p.size()>99 , new BasicRule());
	}


	public static void main(String[] argv) {
		testRandomWithAnamophosisFactor();
	}
}
