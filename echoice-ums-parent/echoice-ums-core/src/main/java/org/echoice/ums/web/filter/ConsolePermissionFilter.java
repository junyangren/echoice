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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.echoice.modules.web.json.bean.ExtJsActionView;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.service.ValidPermissionForUmsService;
import org.echoice.ums.util.CasUmsUtil;
import org.echoice.ums.util.EcumsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;

public class ConsolePermissionFilter implements Filter{
	private ValidPermissionForUmsService validPermissionForUmsService;
	private Logger logger=LoggerFactory.getLogger(ConsolePermissionFilter.class);
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)resp;
		String loginUser=getUser(request);
		if(StringUtils.isNotBlank(loginUser)){
			this.validPermissionForUmsService.setUserPermission(request);
		}else{
			//通过本身的登入界面
			ExtJsActionView actionView=this.validPermissionForUmsService.auth(request);
			if(actionView.getCode()==2001){
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			EcumsUtil.renderJSON(response, JSON.toJSONString(actionView));
			return;
		}
		
		//用户组选择
		EcGroup tmp=CasUmsUtil.getUserGroup(request);
		if(tmp==null){
			String groupId=request.getParameter("_ums_con_groupId");
			if(StringUtils.isNotBlank(groupId)){
				EcGroup group= this.validPermissionForUmsService.getEcGroupDao().findOne(Long.valueOf(groupId));
				CasUmsUtil.setUserGroup(request, group);
			}else{
				List<EcGroup> groupList= this.validPermissionForUmsService.getEcUserDao().findGroupByUserAlias(loginUser);
				if(groupList!=null&&groupList.size()==1){					
					CasUmsUtil.setUserGroup(request, groupList.get(0));
				}else{
					request.setAttribute("groupList", groupList);
					request.getRequestDispatcher("/group_sel.jsp").forward(request, response);
					return;
				}
			}
		}
		
		boolean isAdmin=CasUmsUtil.isAdmin(request);
		//isAdmin=false;
		if(!isAdmin){
			//对用户资源操作权限判断
			String uri=request.getRequestURI();
			String objUrl=StringUtils.substringAfterLast(uri, "/");
			
			String contextPath=request.getContextPath();
			logger.info("{},{}",contextPath,objUrl);
			
			String objAlias=this.validPermissionForUmsService.getConfigBean().getUrlToObjMap().get(objUrl);
			if(StringUtils.isNotBlank(objAlias)){
				String action=request.getParameter("action");
				if(StringUtils.isNotBlank(action)){
					String accessAlias=this.validPermissionForUmsService.getConfigBean().getObjAccessModeMap().get(action);
					if(StringUtils.isNotBlank(accessAlias)){
						accessAlias="edit";
						boolean isPm=this.validPermissionForUmsService.getUmsClientDao().checkHasPermission(loginUser, objAlias, accessAlias);
						logger.info("objUrl:{},objAlias:{},action:{},accessAlias:{},isPm:{}",objUrl,objAlias,action,accessAlias,isPm);
						if(!isPm){
							ExtJsActionView actionView=new ExtJsActionView();
							actionView.setCode(403);
							actionView.addErrorCodeMsg("msg", "对不起，您没有权限");
							EcumsUtil.renderHtml(response, JSON.toJSONString(actionView));
							return;
						}
					}
				}
			}
		}
		
		filterChain.doFilter(req, resp);
	}
	
	protected String getUser(HttpServletRequest request) {
		return CasUmsUtil.getUser(request);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		this.validPermissionForUmsService = (ValidPermissionForUmsService) applicationContext.getBean("validPermissionForUmsService");
	}
}
