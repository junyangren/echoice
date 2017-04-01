package org.echoice.modules.encrypt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * DES3加解密
 * @author junyang
 *
 */
public class DES3Helper {
	private static final String DEFAULT_CHARSET="UTF-8";
	public final static String ALGORITHM="DESede";//DESede //ECB模式 //DESede/ECB/NoPadding
	public final static String ALGORITHM_CBC="DESede/CBC/PKCS7Padding"; //CBC模式
	private final static byte[] DEFAULTIV = {1,2,3,4,5,6,7,8};
	public final static IvParameterSpec IVSPEC=new IvParameterSpec(DEFAULTIV);
	static{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	/**
	 * 加密
	 * @param key
	 * @param src
	 * @param algorithm
	 * @param ivspec
	 * @return
	 */
	public static byte[] encryptBytes(byte key[],String src,String algorithm,IvParameterSpec ivspec){
		try {
			SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, algorithm);
			Cipher c = Cipher.getInstance(algorithm);
			c.init(Cipher.ENCRYPT_MODE, deskey,ivspec);
			byte[] tmpBytes = src.getBytes(DEFAULT_CHARSET);
			// 加密，结果保存进cipherByte
			byte[] cipherByte = c.doFinal(tmpBytes);
			return cipherByte;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 加密
	 * @param key
	 * @param src
	 * @param algorithm
	 * @param ivspec
	 * @return
	 */
	public static byte[] encryptBytes(String key,String src,String algorithm,IvParameterSpec ivspec){
		return encryptBytes(key.getBytes(),src,algorithm,ivspec);
	}
	
	/**
	 * 加密，取src字节用UTF-8编码，加密结果为16进制格式字符串
	 * @param key
	 * @param src
	 * @param algorithm
	 * @return
	 */	
	public static String encrypt(String key,String src,String algorithm,IvParameterSpec ivspec){
		byte tmp[]=encryptBytes(key,src,algorithm,ivspec);
		if(tmp!=null){
			String str=HexUtil.byteArr2HexStr(tmp);
			return str;
		}
		return null;
	}
	/**
	 * 加密，取src字节用UTF-8编码，加密结果为base64字符串
	 * @param key
	 * @param src
	 * @param algorithm
	 * @param ivspec
	 * @return
	 */
	public static String encryptForOutBase64(byte key[],String src,String algorithm,IvParameterSpec ivspec){
		byte tmp[]=encryptBytes(key,src,algorithm,ivspec);
		if(tmp!=null){
			String str=new BASE64Encoder().encode(tmp);
			//String str=com.ffcs.mc.encrypt.BASE64Encoder.encode(tmp);
			return str;
		}
		return null;
	}
	
	/**
	 * 加密，取src字节用UTF-8编码，加密结果为base64字符串
	 * @param key
	 * @param src
	 * @param algorithm
	 * @param ivspec
	 * @return
	 */
	public static String encryptForOutBase64(String key,String src,String algorithm,IvParameterSpec ivspec){
		return encryptForOutBase64(key.getBytes(),src,algorithm,ivspec);
	}
	/**
	 * 加密，取src字节用UTF-8编码，加密结果为base64字符串
	 * @param key
	 * @param src
	 * @param algorithm
	 * @return
	 */
	public static String encryptForOutBase64(byte key[],String src,String algorithm){
		return encryptForOutBase64(key,src,algorithm,IVSPEC);
	}
	
	/**
	 * 加密，取src字节用UTF-8编码，加密结果为base64字符串,默认初始向量12345678
	 * @param key
	 * @param src
	 * @param algorithm
	 * @return
	 */
	public static String encryptForOutBase64(String key,String src,String algorithm){
		return encryptForOutBase64(key,src,algorithm,IVSPEC);
	}
	/**
	 * 加密，取src字节用UTF-8编码，加密结果为16进制格式字符串,默认初始向量12345678
	 * @param key
	 * @param src
	 * @param algorithm 
	 * @return
	 */
	public static String encrypt(String key,String src,String algorithm){
		return encrypt(key,src,algorithm,IVSPEC);
	}
	
	/**
	 * 加密，取src字节用UTF-8编码，加密结果为16进制格式字符串（默认使用ECB）
	 * @param key
	 * @param src
	 * @return
	 */
	public static String encrypt(String key,String src){
		return encrypt(key,src,ALGORITHM,null);
	}
	/**
	 * 解密
	 * @param key
	 * @param srcBytes
	 * @param algorithm
	 * @param ivspec
	 * @return
	 */
	public static String decrypt(String key,byte[] srcBytes,String algorithm,IvParameterSpec ivspec){
		try {			
			SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key.getBytes(), algorithm);
			Cipher c = Cipher.getInstance(algorithm);
			c.init(Cipher.DECRYPT_MODE, deskey,ivspec);
			// 加密，结果保存进cipherByte
			byte[] cipherByte = c.doFinal(srcBytes);
			String tmp=new String(cipherByte,DEFAULT_CHARSET);
			return tmp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 解密base64加密串
	 * @param key
	 * @param base64Src
	 * @param algorithm
	 * @param ivspec
	 * @return
	 */
	public static String decryptForInBase64(String key,String base64Src,String algorithm,IvParameterSpec ivspec){
		try {
			byte[] tmpBytes = new BASE64Decoder().decodeBuffer(base64Src);
			return decrypt(key,tmpBytes,algorithm,ivspec);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 解密base64加密串,默认初始向量12345678
	 * @param key
	 * @param base64Src
	 * @param algorithm
	 * @return
	 */
	public static String decryptForInBase64(String key,String base64Src,String algorithm){
		return decryptForInBase64(key,base64Src,algorithm,IVSPEC);
	}
	/**
	 * 解密16进制加密串
	 * @param key
	 * @param hexSrc
	 * @param algorithm
	 * @return
	 */
	public static String decrypt(String key,String hexSrc,String algorithm,IvParameterSpec ivspec){
		byte[] tmpBytes = HexUtil.hexStr2ByteArr(hexSrc);
		return decrypt(key,tmpBytes,algorithm,ivspec);
	}
	
	/**
	 * 解密16进制加密串,默认初始向量12345678
	 * @param key
	 * @param hexSrc
	 * @param algorithm
	 * @return
	 */
	public static String decrypt(String key,String hexSrc,String algorithm){
		return decrypt(key,hexSrc,algorithm,IVSPEC);
	}
	
	/**
	 * 解密16进制加密串（默认使用ECB）
	 * @param key
	 * @param hexSrc
	 * @return
	 */
	public static String decrypt(String key,String hexSrc){
		return decrypt(key,hexSrc,ALGORITHM,null);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {		
//		String msg="1354774934752105$p99999999";
//		String key=RandomKey.getKey(24);
//		key="GtAdmfeeFJMhVFP0bjwKX4Km";
//		String hexStr=DES3Helper.encrypt(key, msg,DES3Helper.ALGORITHM_CBC);
//		String decryptStr=DES3Helper.decrypt(key, hexStr,DES3Helper.ALGORITHM_CBC);
//		System.out.println("src:"+msg);
//		System.out.println("hexStr:"+hexStr);
//		System.out.println("decryptStr:"+decryptStr);//
//		System.out.println(HexUtil.byteArr2HexStr(key.getBytes()));
//		//String tmp=Cryto.encryptBase643DES(msg, HexUtil.byteArr2HexStr(key.getBytes()));
//		//System.out.println("base64Str:"+tmp);
//		hexStr=DES3Helper.encryptForOutBase64(key, msg,DES3Helper.ALGORITHM_CBC);
//		System.out.println("descryptStr:"+hexStr);	
		String msg="139485748574854messagecenterIFW@NFI@#Jfie";
		String key=RandomKey.getKey(24);
		key="d3wCluDKsWWq0XNs3iAfjVHl";
		String tmp="13344166d6464665a4d686556465030626a9494b58344b6d";
		System.out.println(tmp.length());
		byte tmpbyte[]=HexUtil.hexStr2ByteArr(tmp);
		
		String hexStr=DES3Helper.encryptForOutBase64(tmpbyte, msg,DES3Helper.ALGORITHM_CBC);
		System.out.println("hexStr1:"+hexStr);
		
		//String hexStr2=Cryto.encryptBase643DES(msg, tmp);
		
		//System.out.println("hexStr2:"+hexStr2);
	}
}
