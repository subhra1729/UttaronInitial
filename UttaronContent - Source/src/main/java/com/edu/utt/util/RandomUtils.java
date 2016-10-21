/**
 * 
 */
package com.edu.utt.util;

import java.util.Random;

/**
 * @author kabas
 *
 */
public class RandomUtils {
	
	private static final char[] symbols;
	
	static{
		StringBuilder tmpBldr = new StringBuilder();
		for(char ch = '0'; ch < '9' ; ch++){
			tmpBldr.append(ch);
		}
		for(char ch = 'a'; ch < 'z' ; ch++){
			tmpBldr.append(ch);
		}
		symbols = tmpBldr.toString().toCharArray();
	}
	
//	private final Random random = new Random();
	
	public static String getRandom(int lengthOfNumber){
		Random random = new Random();
		char[] buf = 	new char[lengthOfNumber];
		for(int count = 0; count < lengthOfNumber; count++){
			buf[count] =  symbols[random.nextInt(symbols.length)];
		}
		return new String(buf);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getRandom(8));

	}

}
