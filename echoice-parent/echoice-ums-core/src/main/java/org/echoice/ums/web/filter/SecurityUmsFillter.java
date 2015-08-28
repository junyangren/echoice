package org.echoice.ums.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.echoice.ums.dao.UmsClientDao;
import org.echoice.ums.domain.EcObjects;
import org.jasig.cas.client.util.AssertionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SecurityUmsFillter implements Filter{
	private static final Logger logger=LoggerFactory.getLogger(SecurityUmsFillter.class);
	private boolean isSecurityFilter=true;
	private String userSecurityObjectsSessionName="_USER_SECURITY_UMS_OBJECTS";
	private static String accessModeAlias="view";
	private static String urlTag="url";
	private static Map<String,EcObjects> securityObjectMap=new HashMap<String, EcObjects>();
	private static Map<String,List<EcObjects>> securityObjectListMap=new HashMap<String, List<EcObjects>>();
	private static Map<String,String> excludeSecurityMap=new HashMap<String, String>();
	
	private static UmsClientDao umsClientDao;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		String isSecurityFilterS=filterConfig.getInitParameter("isSecurityFilter");
		isSecurityFilter=Boolean.valueOf(isSecurityFilterS);
		accessModeAlias=filterConfig.getInitParameter("accessModeAlias");
		userSecurityObjectsSessionName=filterConfig.getInitParameter("userSecurityObjectsSessionName");
		urlTag=filterConfig.getInitParameter("urlTag");
		String excludeSecurityUrls=filterConfig.getInitParameter("excludeSecurityUrls");
		if(excludeSecurityUrls!=null&&!("".equals(excludeSecurityUrls))){
			String excludeSecurityUrlArr[]=excludeSecurityUrls.split("\\|");
			for (int i = 0; i < excludeSecurityUrlArr.length; i++) {
				excludeSecurityMap.put(excludeSecurityUrlArr[i], excludeSecurityUrlArr[i]);
			}
			
		}
		
		WebApplicationContext applicationContext=WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		umsClientDao=(UmsClientDao)applicationContext.getBean("umsClientDao");
		initSecurityObjectMap();
	}
	/**
	 * 初始化系统保护资源对象
	 */
	public static void initSecurityObjectMap(){
		List<EcObjects> menuList=umsClientDao.findObjectsByAccessModeAlias(accessModeAlias);
		JSONObject jsonObject=null;
		String url=null;
		int index=0;
		String exclueUrl=null;
		List<EcObjects> tmpList=null;
		for (EcObjects ecObjects : menuList) {
			logger.debug(""+ecObjects.getAlias()+","+ecObjects.getName()+","+ecObjects.getNote());
			if(ecObjects.getNote()!=null&&!("".equals(ecObjects.getNote()))){
				try{
					jsonObject=JSON.parseObject(ecObjects.getNote());
					url=jsonObject.getString(urlTag);
					index=url.indexOf("?");
					if(index!=-1){
						url=url.substring(0,index);
					}
					
					if('/'!=url.charAt(0)&&(url.indexOf("http://")==-1)&&(url.indexOf("https://")==-1)){
						url="/"+url;
					}
					
					logger.debug("urlPath:"+url);
					//排除url
					exclueUrl=excludeSecurityMap.get(url);
					if(exclueUrl==null){
						//securityObjectMap.put(url, ecObjects);
						tmpList=securityObjectListMap.get(url);
						if(tmpList==null){
							tmpList=new ArrayList<EcObjects>();
						}
						tmpList.add(ecObjects);
						securityObjectListMap.put(url, tmpList);
					}
				}catch (Exception e) {
					// TODO: handle exception
					//logger.error("parseJson erro:",e);
				}
			}
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpReq=(HttpServletRequest)request;
		if(isSecurityFilter){
			//获取访问地址
			String urlPath=httpReq.getServletPath();
			//查看是否受保护URL地址
			//EcObjects secObject=securityObjectMap.get(urlPath);
			List<EcObjects> secObjectList=securityObjectListMap.get(urlPath);
			if(secObjectList!=null){
				//取用获得的权限的资源对象，并放入session中。
				Object userSecurityObj=httpReq.getSession().getAttribute(userSecurityObjectsSessionName);
				Map<String,EcObjects> userSecurityObjectMap=null;
				if(userSecurityObj==null){
					userSecurityObjectMap=new HashMap<String, EcObjects>();
					String userName=AssertionHolder.getAssertion().getPrincipal().getName();
					List<EcObjects> list=umsClientDao.findAssignPermissionObjectList(userName, this.accessModeAlias);
					if(list!=null&&list.size()>0){
						for (EcObjects ecObjects : list) {
							logger.debug("userSecurityObject:"+ecObjects.getAlias()+","+ecObjects.getName());
							userSecurityObjectMap.put(ecObjects.getAlias(), ecObjects);
						}
					}
					httpReq.getSession().setAttribute(userSecurityObjectsSessionName, userSecurityObjectMap);
				}else{
					userSecurityObjectMap=(Map<String,EcObjects>)userSecurityObj;
				}
				//比对资源权限
				EcObjects judgeObject=null;
				boolean isPm=false;
				for (EcObjects secObject : secObjectList) {
					judgeObject=userSecurityObjectMap.get(secObject.getAlias());
					if(judgeObject!=null){
						isPm=true;
						break;
					}
				}
				
				if(!isPm){
					//logger.debug(secObject.getName()+","+secObject.getAlias()+","+urlPath+",没有权限！！");
					//httpReq.setAttribute("tipMsg","【"+secObject.getName()+"】模块，无访问权限！");
					httpReq.setAttribute("tipMsg","对不起，您没有该模块的访问权限，请联系管理 员！");
					request.getRequestDispatcher("/showTipMsg.jsp").forward(request, response);
					return;
				}else{
					//logger.debug(secObject.getName()+","+secObject.getAlias()+","+urlPath+",有权限！！");
				}
				
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		securityObjectMap.clear();
		securityObjectListMap.clear();
		umsClientDao=null;
	}

}
