package org.echoice.modules.encrypt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
/**
 * rsa密钥生成
 * @author junyang
 *
 */
public class RSAKey {
	private PrivateKey privateKey = null;
	private PublicKey publicKey = null;
	
	static{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	public RSAKey() {
		try {
			//KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
			//KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSAHelper.DEFAULT_ALGORITHM);
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSAHelper.DEFAULT_ALGORITHM,RSAHelper.DEFAULT_PROVIDER);
			
			// 初始化密钥对生成器，密钥大小为1024位
			keyPairGen.initialize(1024);
			// 生成一个密钥对，保存在keyPair中
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 得到私钥
			privateKey = (RSAPrivateKey) keyPair.getPrivate();
			// 得到公钥
			publicKey = (RSAPublicKey) keyPair.getPublic();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPublicKeyString() {
		try {
			return RSAHelper.getKeyString(publicKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getPrivateKeyString() {
		try {
			return RSAHelper.getKeyString(privateKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	
	public static void main(String[] args) {
		
	}
}
