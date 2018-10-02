package org.echoice.ums.web.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.echoice.ums.config.ConfigConstants;
import org.echoice.ums.dao.UmsClientDao;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.web.UmsAppBean;
import org.echoice.ums.web.UmsHolder;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SecurityPmFilter implements Filter{
	private static final Logger logger=LoggerFactory.getLogger(SecurityPmFilter.class);
	public static final String CONST_UMS_GROUP="CONST_UMS_GROUP";

	private UmsClientDao umsClientDao;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		logger.info("init param");
		WebApplicationContext applicationContext=WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		umsClientDao=(UmsClientDao)applicationContext.getBean("umsClientDao");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		try {
			HttpServletRequest httpReq=(HttpServletRequest)request;
			//模拟数据
			simulatedData(httpReq);
			
			UmsAppBean umsAppBean=new UmsAppBean();
			String userName=getUserName(httpReq);
			//
	    	boolean isAdmin=(boolean)httpReq.getSession().getAttribute(ConfigConstants.IS_SUPER_ADMIN);
	    	umsAppBean.setAdmin(isAdmin);
			umsAppBean.setUserAlias(userName);
			
			//获取用户组信息
			EcGroup userGroup=(EcGroup)httpReq.getSession(false).getAttribute(ConfigConstants.UMS_GROUP_SESSION);
			if(userGroup==null) {
				List<EcGroup> ecGroupList=umsClientDao.findGroupsByUserAlias(userName);
				if(ecGroupList!=null&&ecGroupList.size()>0) {
					userGroup=ecGroupList.get(0);
					httpReq.getSession(false).setAttribute(ConfigConstants.UMS_GROUP_SESSION, userGroup);
				}
			}
			
			umsAppBean.setEcGroup(userGroup);
			UmsHolder.setUmsAppBean(umsAppBean);
			
			chain.doFilter(request, response);
		}finally {
			UmsHolder.clear();
		}
	}
	
    private String getUserName(HttpServletRequest request){
    	Object obj=request.getSession().getAttribute(AuthenticationFilter.CONST_CAS_ASSERTION);
    	if (obj instanceof Assertion) {
			Assertion assertion = (Assertion) obj;
			return assertion.getPrincipal().getName();
		}else{
			return (String)obj;
		}
    }
	
	private void simulatedData(HttpServletRequest request) {
		request.getSession().setAttribute(ConfigConstants.IS_SUPER_ADMIN, true);
		request.getSession().setAttribute(AuthenticationFilter.CONST_CAS_ASSERTION,"admin");
		//List<EcGroup> groupList=umsClientDao.findGroupsByUserAlias("test");
		//CasUmsUtil.setUserGroup(request, groupList.get(0));
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
