package org.echoice.modules.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class XssJsoupFilter implements Filter {
	private int filterType=1;
	private String excludeUrlArr[]=null;
	private static final Logger logger=LoggerFactory.getLogger(XssJsoupFilter.class);
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		String filterTypeStr=filterConfig.getInitParameter("filterType");
		if(filterTypeStr!=null&&!("".equals(filterTypeStr))){
			filterType=Integer.parseInt(filterTypeStr);
		}
		
		String excludeUrls=filterConfig.getInitParameter("excludeUrls");
		if(excludeUrls!=null&&!("".equals(excludeUrls))){
			logger.info("excludeUrls:"+excludeUrls);
			excludeUrlArr=excludeUrls.split("\\|");
			for (int i = 0; i < excludeUrlArr.length; i++) {
				logger.info(i+"excludeUrl:"+excludeUrlArr[i]);
			}
		}
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq=(HttpServletRequest) request;
		logger.debug("XssFilter,method:"+httpReq.getMethod()+",path:"+httpReq.getServletPath()+",action:"+httpReq.getParameter("action"));
		boolean isFilter=true;
		if(excludeUrlArr!=null&&excludeUrlArr.length>0){
			String url=httpReq.getServletPath();
			for (int i = 0; i < excludeUrlArr.length; i++) {
				if(url.equals(excludeUrlArr[i])){
					logger.debug("excludeUrl:"+url);
					isFilter=false;
					break;
				}
			}
		}
		if(isFilter){
			chain.doFilter(new XssJsoupHttpServletRequestWrapper((HttpServletRequest) request,this.filterType), response);
		}else{
			chain.doFilter(request, response);
		}
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
