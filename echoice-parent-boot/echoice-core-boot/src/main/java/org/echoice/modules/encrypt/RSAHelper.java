package org.echoice.modules.encrypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAHelper {
	public static final String DEFAULT_CHARSET="UTF-8";
	public static final String DEFAULT_ALGORITHM="RSA";
	public static final String DEFAULT_PROVIDER="BC";
	static{
		//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	/**
	 * 根据16进制公钥字符串生成PublicKey对象
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String hexKey) {
		try{
			byte[] keyBytes = HexUtil.hexStr2ByteArr(hexKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(DEFAULT_ALGORITHM,DEFAULT_PROVIDER);
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据16进制私钥字符串生成PrivateKey对象
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String hexKey) throws Exception {
		try{
			byte[] keyBytes = HexUtil.hexStr2ByteArr(hexKey);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(DEFAULT_ALGORITHM,DEFAULT_PROVIDER);
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
			return privateKey;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 将公钥或私钥转换成16进制字符串
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		String s = HexUtil.byteArr2HexStr(keyBytes);
		return s;
	}
	/**
	 * RSA加密
	 * @param key
	 * @param srcBytes
	 * @return
	 */
	public static byte[] encrypt(Key key, byte[] srcBytes){
		if (key != null) {
			try {
				Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM,DEFAULT_PROVIDER);
				// 根据公钥，对Cipher对象进行初始化
				cipher.init(Cipher.ENCRYPT_MODE, key);
				byte[] resultBytes = cipher.doFinal(srcBytes);
				return resultBytes;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 将字符串rsa加密返回16进制加密串格式
	 * @param key
	 * @param src
	 * @return
	 */
	public static String encrypt(Key key, String src){
		if (key != null) {
			try {
				byte[] resultBytes=encrypt(key,src.getBytes(DEFAULT_CHARSET));
				String tmp=HexUtil.byteArr2HexStr(resultBytes);
				return tmp;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * RSA解密
	 * @param key
	 * @param srcBytes
	 * @return
	 */
	public static byte[] decrypt(Key key, byte[] srcBytes){
		if (key != null) {
			try {
				// Cipher负责完成加密或解密工作，基于RSA
				Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM,DEFAULT_PROVIDER);
				// 根据公钥，对Cipher对象进行初始化
				cipher.init(Cipher.DECRYPT_MODE, key);
				byte[] resultBytes = cipher.doFinal(srcBytes);
				return resultBytes;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 
	 * @param key
	 * @param hexStr
	 * @return
	 */
	public static String decrypt(Key key, String hexStr){
		if (key != null) {
			try {
				byte srcBytes[]=HexUtil.hexStr2ByteArr(hexStr);
				byte[] resultBytes=decrypt(key,srcBytes);
				String tmp=new String(resultBytes,DEFAULT_CHARSET);
				return tmp;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
