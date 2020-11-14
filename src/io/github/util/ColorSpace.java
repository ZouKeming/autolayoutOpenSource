package io.github.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.scene.paint.Color;

public class ColorSpace {
	double e = Math.sqrt(3);	// SQRT( RED*RED + GREEN*GREEN + BLUE*BLUE )

	public static double colorDistance(Color a, Color b) {
		double red = a.getRed() - b.getRed();
		double green = a.getGreen() - b.getGreen();
		double blue = a.getBlue() - b.getBlue();

		return Math.sqrt(red*red + green * green + blue * blue)
	}

	public static String string2Color(String input, Boolean light) {
		try { 
			// getInstance() method is called with algorithm SHA-384 
			MessageDigest md = MessageDigest.getInstance("SHA-512"); 

			// digest() method is called 
			// to calculate message digest of the input string 
			// returned as array of byte 
			byte[] messageDigest = md.digest(input.getBytes()); 

			// Convert byte array into signum representation 
			BigInteger no = new BigInteger(1, messageDigest); 
			
			int mask = 0x3f;
			int color = no.mod(new BigInteger("1000000", 16)).intValue();
			int red = color>>16 & mask;
			int green = color>>8 & mask;
			int blue = color & mask;
			
			mask = 0xff;
			if( light ) {
				red = red ^ mask;
				blue = blue ^ mask;
				green = green ^ mask;
			}
			return String.format("#%02X%02X%02X", red, green, blue);
		} 

		// For specifying wrong message digest algorithms 
		catch (NoSuchAlgorithmException e) { 
			throw new RuntimeException(e); 
		} 

	}
	
	public static void main(String[] argv) {
		System.out.println(ColorSpace.string2Color("d"));
	}
}
