package io.github.rhino;

import java.util.Vector;

public class RandomVocabulary {
	static char[] vowels = { 'a', 'i', 'e', 'o', 'u' };

	public RandomVocabulary(int number) {
		for(int i=0; i<number; i++) {
			vectorString.add( randomWord(false) );
		}
	}

	public static String randomWord(boolean firstLetterUppercase) {
		StringBuilder sb = new StringBuilder();
		for( int i=0; i<5*Math.random(); i++) {
			char prefix1 = (char)('a' + 26 * Math.random());
			if(i==0 && firstLetterUppercase) {
				prefix1 += (char)('A' - 'a');
			}
			char randomVowel = vowels[(int) (vowels.length * Math.random())];
			char surfix1 = (char)('a' + 26 * Math.random());
			if(Math.random() > 0.8 || (i==0 && firstLetterUppercase) )
				sb.append(prefix1);
			sb.append(randomVowel);
			sb.append(surfix1);
		} 
		return sb.toString();
	}

	Vector<String> vectorString = new Vector<String>();

	public void print() {
		for(int i=0; i<vectorString.size(); i++) {
			System.out.println(vectorString.get(i));
		}
	}

	public static int wordsInSentence() {
		return (int) (3 + 4 * Math.random());
	}
	public static void randomDocument(int length) {
		StringBuilder text = new StringBuilder();
		StringBuilder sentence = new StringBuilder();
		int wis = wordsInSentence();
		int wisCounter=1;
		for (int i=1; i<length; i++) {
			String randomWord;
			if( wisCounter==1 ) {
				randomWord = randomWord(true);
			} else {
				randomWord = randomWord(false);
			}
			sentence.append(randomWord);
			if(wisCounter++ % wis == 0) { 
				sentence.append('.');
				sentence.append(' ');
				wis = wordsInSentence();
				wisCounter=1;
			} else {
				sentence.append(" ");
			}
			
			if( sentence.length()>80) {
				text.append(sentence + "\n");
				sentence = new StringBuilder();
			}
		};
		System.out.println(text.toString());
	}

	public static void main(String[] argv) {
		RandomVocabulary vocabulary =  new RandomVocabulary(100);
		vocabulary.print();
		
		randomDocument(999);
	}
}
