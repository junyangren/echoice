package org.echoice.modules.cas;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.util.AssertionHolder;
/**
 * 取得cas登入用户名
 * @author wujy
 *
 */
public class CasUtil {
	public static final String USER_SESSION_KEY="org.echoice.ums.userSession";
	
	public static final String CONST_CAS_ASSERTION=AuthenticationFilter.CONST_CAS_ASSERTION;
	
	public static final String VALIDATECODE_SESSION_NAME="VALIDATECODE_SESSION_NAME";;
	/**
	 * 查看cas登入用户
	 * @return
	 */
	public static String getUserName(){
		String name=AssertionHolder.getAssertion().getPrincipal().getName();
		return name;
	}
}
