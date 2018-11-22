package org.echoice.ums.util;

import org.apache.shiro.crypto.hash.ConfigurableHashService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.echoice.modules.encrypt.MD5;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordEncoderUtil {
    private static final String ALGORITHMNAME = "MD5";
    private static final String STATICSALT ="ecums";
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(16);
    
    public static PasswordEncoder get() {
    	StandardPasswordEncoder passwordEncoder=new StandardPasswordEncoder(STATICSALT);
    	return passwordEncoder;
    }
    
	public static String encode(String rawPasswd,String staticSalt,String dynaSalt) {
		ConfigurableHashService hashService = new DefaultHashService();
        hashService.setPrivateSalt(ByteSource.Util.bytes(staticSalt));
        hashService.setHashAlgorithmName(ALGORITHMNAME);
        hashService.setHashIterations(2);
        HashRequest request = new HashRequest.Builder()
                .setSalt(dynaSalt)
                .setSource(rawPasswd)
                .build();
        String enPwd =  hashService.computeHash(request).toHex();
        return enPwd;
	}
	
	
	public static String encode(String rawPasswd,String dynaSalt) {
		return encode(rawPasswd,STATICSALT,dynaSalt);
	}
	
	@Deprecated
	public static String encodeMd5(String rawPasswd,String staticSalt) {
		String password=staticSalt+rawPasswd;
		MD5 md5=new MD5();
		String md5Password=md5.getMD5ofStr(password);
		return md5Password;
	}
	
	
}
