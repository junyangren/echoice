package org.echoice.modules.encrypt;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class HexUtil {
	private static final char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	public static String byteHEX(byte ib) {
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}
	/**
	 * 使用位移实现
	 * @param ibArr
	 * @return
	 */
	public static String byteHEX(byte[] ibArr) {
		char[] ob=null;
		StringBuilder builder=new StringBuilder();
		for (int i = 0; i < ibArr.length; i++) {
			ob = new char[2];
			ob[0] = Digit[(ibArr[i] >>> 4) & 0X0F];
			ob[1] = Digit[ibArr[i] & 0X0F];
			builder.append(ob);
		}
		return builder.toString();
	}
	
	/**
	 * 使用Integer.toHexString实现
	 * @param ibArr
	 * @return
	 */
	public static String byteHEXOfInteger(byte[] ibArr) {
		StringBuilder builder=new StringBuilder();
		String tmp=null;
		for (int i = 0; i < ibArr.length; i++) {
			tmp = (java.lang.Integer.toHexString(ibArr[i] & 0XFF));
			if(tmp.length()==1){
				builder.append("0");
			}
			builder.append(tmp);
		}
		return builder.toString().toUpperCase();
	}
	
	
	/**
	 * 字节转16进制字符串
	 * @param arrB
	 * @return
	 */
	public static String byteArr2HexStr(byte[] arrB) {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		int intTmp;
		for (int i = 0; i < iLen; i++) {
			intTmp = arrB[i];
			/**
			String temp = Integer.toHexString(arrB[i] & 0xFF);
			if (temp.length() == 1) {
				sb.append("0" + temp);
			} else {
				sb.append(temp);
			}**/
			
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}
	/**
	 * 16进制字符串转字节
	 * @param strIn
	 * @return
	 */
	public static byte[] hexStr2ByteArr(String strIn) {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	
	public static byte[] strToByte(String inStr) {
		byte[] byteArray = new byte[inStr.length()];
		for (int i = 0; i < inStr.length(); i++) {
			byteArray[i]=Byte.parseByte(String.valueOf(inStr.charAt(i)));
		}
		return byteArray;
	}
	
	/**
	 * Hex编码.
	 */
	public static String encodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException(e);
		}

	}
	
	public static void main(String[] args) {
		byte a=10;
		System.out.println(byteHEX(a));
		System.out.println(byteHEXOfInteger(new byte[]{a}));
		System.out.println(Integer.toHexString(a));
		System.out.println(Integer.toHexString(a>>>4));
		Integer b=7800;
		int c=0x1e78;
		System.out.println(Integer.toHexString(b)+":"+c);
	}
}
