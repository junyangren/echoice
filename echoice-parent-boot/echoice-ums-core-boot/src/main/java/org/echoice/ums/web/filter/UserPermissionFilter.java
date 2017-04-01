package org.echoice.ums.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.echoice.ums.service.ValidPermissionForUmsService;
import org.echoice.ums.util.CasUmsUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 用户是否登入，及权限过滤(无用)
 * @author junyang
 *
 * 2007-3-16
 */
@Deprecated
public class UserPermissionFilter implements Filter {
	private String allowedFiles;
	private String fileArr[];
	private ValidPermissionForUmsService validPermissionForUmsService;
	public void destroy() {
		// TODO Auto-generated method stub
		allowedFiles=null;
		fileArr=null;
	}

	public void doFilter(ServletRequest req, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest request=(HttpServletRequest)req;
		String uri=request.getRequestURI();
		int count=0;
		for (int i = 0; i < fileArr.length; i++) {
			if(StringUtils.indexOf(uri, fileArr[i])==-1){
				count++;
			}
		}
		if(count==fileArr.length){
			String alias=getUser(request);
			if(StringUtils.isBlank(alias)){
				request.getRequestDispatcher("/jump.jsp").forward(request, response);
				return ;
			}
			Object tmp=request.getSession().getAttribute("accordion");
			if(tmp==null){	
				validPermissionForUmsService.setUserPermission(request);
			}	
		}	
		filterChain.doFilter(req, response);
	}
	
    protected String getUser(HttpServletRequest request){
    	return CasUmsUtil.getUser(request);
    }

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.allowedFiles=filterConfig.getInitParameter("AllowedFiles");
		fileArr=StringUtils.split(allowedFiles,"|");
		WebApplicationContext applicationContext=WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		validPermissionForUmsService=(ValidPermissionForUmsService)applicationContext.getBean("validPermissionForUmsService");
	}

}
