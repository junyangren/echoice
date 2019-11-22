package org.echoice.ums.util;

import org.apache.commons.lang3.StringUtils;
import org.echoice.modules.encrypt.MD5;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
	
	public static String encode(String rawPassword,String salt,int type) {
		if(type==1) {
			MD5 md5=new MD5();
			String encodedPassword=md5.getMD5ofStr(salt+rawPassword);
			return encodedPassword;
		}else {
			BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
			String encodedPassword=passwordEncoder.encode(rawPassword);
			return encodedPassword;
		}
	}
	
	public static boolean matches(String rawPassword,String salt, String encodedPassword,int type) {
		if(type==1) {
			MD5 md5=new MD5();
			String encodedPassword1=md5.getMD5ofStr(salt+rawPassword);
			if(StringUtils.equalsIgnoreCase(encodedPassword1, encodedPassword)) {
				return true;
			}
		}else {
			BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
			boolean r=passwordEncoder.matches(rawPassword, encodedPassword);
			return r;
		}
		return false;
	}
}
