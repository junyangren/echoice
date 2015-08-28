package org.echoice.modules.encrypt;

import java.util.Random;
/**
 * 生成字母+数字的随机密钥串
 * @author wujy
 *
 */
public class RandomKey {
	private static final char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'd', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9' };

	public static String getKey(int length) {
		Random random = new Random();
		StringBuilder bf = new StringBuilder();
		char tmp;
		int randCount = codeSequence.length;
		for (int i = 0; i < length; i++) {
			tmp = codeSequence[random.nextInt(randCount)];
			bf.append(tmp);
		}
		return bf.toString();
	}
	
	public static String getKey24() {
		return getKey(24);
	}
	
	public static String getKey8() {
		return getKey(8);
	}

	public static void main(String[] args) {
		System.out.println(getKey24());
	}
}
