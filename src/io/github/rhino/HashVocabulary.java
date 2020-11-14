package io.github.rhino;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

public class HashVocabulary extends RandomVocabulary {

	public HashVocabulary() {
		super(0);
	}

	public HashVocabulary(int number) {
		super(number);
	}

	Vector<String> vectorString = new Vector<String>();

	public void print() {
		for(int i=1; i<33; i++) {
			System.out.println(i + "\t" + hashWord(i, false));
		}
	}
	
	public static String hashWord(int num) {
		return hashWord(num, false);
	}

	public static String hashWord(int num, boolean firstLetterUppercase) {
		byte[] bytesOfMessage;
		try {
			bytesOfMessage = new Integer(num).toString().getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);

//			for(int i=0; i<thedigest.length; i++) {
//				System.out.printf("%X", thedigest[i]);
//			}
//			System.out.println("\t" + thedigest.length);

			char prefix, vowel, surfix;
			int index;
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<16-3; i+=3) {
				int o = thedigest[i] & 0xFF;
				int p = thedigest[i+1] & 0xFF;
				int s = thedigest[i+2] & 0xFF;

				if ( i == 0 ) {
					index = p % 27;
					if( index < 26 && isConsonant(index) ) {
						char ch = (char) ('a' + index );
						if(firstLetterUppercase) {
							ch += (char)('A' - 'a');
						}
						sb.append( ch );
					}

					index = o % 5;
					sb.append( vowels[index] );	

					index = (s ^ 78) % 30;
					if( index < 26 && isConsonant(index)  )
						sb.append( (char) ('a' + index ) );
				} else {
					int vowelIndex = o % (5 + i);
					if( vowelIndex < 5 ) {
						index = p % 27;
						if( index < 26  && isConsonant(index) )	
							sb.append( (char) ('a' + index ) );

						sb.append( vowels[vowelIndex] );	

						index = (s ^ 78) % 30;
						if( index < 26  && isConsonant(index) )
							sb.append( (char) ('a' + index ) );
					}
				}
			}
			
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	private static boolean isConsonant(int index) {
		char ch = (char) ('a' + index);
		for(int i=0; i<vowels.length; i++) {
			if ( ch == vowels[i] ) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] argv) {
		HashVocabulary vocabulary =  new HashVocabulary();
		vocabulary.print();

	}
}
