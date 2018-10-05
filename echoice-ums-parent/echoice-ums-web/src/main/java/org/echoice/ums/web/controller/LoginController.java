package org.echoice.ums.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.echoice.modules.cas.CasUtil;
import org.echoice.modules.encrypt.MD5;
import org.echoice.modules.web.MsgTip;
import org.echoice.modules.web.json.bean.ExtJsActionView;
import org.echoice.ums.config.ConfigBean;
import org.echoice.ums.dao.EcGroupDao;
import org.echoice.ums.dao.EcUserDao;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.service.ValidPermissionForUmsService;
import org.echoice.ums.util.CasUmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController{
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ConfigBean configBean;
	@Autowired
	private EcUserDao ecUserDao;
	@Autowired
	private EcGroupDao ecGroupDao;
	@Autowired
	private ValidPermissionForUmsService validPermissionForUmsService; 
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return "login";
	}

	@RequestMapping(value="",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public MsgTip login(HttpServletRequest request,HttpServletResponse response) throws Exception {
		MsgTip msgTip=new MsgTip();
		String userAlias=request.getParameter("loginName");
		String password=request.getParameter("password");
		List<EcUser> list=ecUserDao.findByAlias(userAlias);
		if(list!=null&&list.size()>0){
			EcUser ecUser=list.get(0);
			String passWordDb=ecUser.getPassword();
			MD5 md5=new MD5();
			String userPassWord=md5.getMD5ofStr(userAlias+password);
			//用户密码校验
			if(passWordDb.equals(userPassWord)){
				request.getSession().setAttribute(CasUtil.CONST_CAS_ASSERTION, ecUser.getAlias());
			}else{
				logger.info(userAlias+"：用户密码错误");
				msgTip.setCode(302);
				msgTip.setMsg("对不起，用户密码错误");
			}
		}else{
			logger.info(userAlias+"：用户名错误");
			msgTip.setCode(301);
			msgTip.setMsg("对不起，用户名错误");
		}	
		return msgTip;
	}
	
	@RequestMapping(params={"action=selGroup"})
	public ModelAndView selGroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String groupId=request.getParameter("groupId");
		EcGroup group=ecGroupDao.findOne(Long.valueOf(groupId));
		CasUmsUtil.setUserGroup(request, group);
		return new ModelAndView("redirect:/index.jsp");
	}
	
	public EcUserDao getEcUserDao() {
		return ecUserDao;
	}
	public void setEcUserDao(EcUserDao ecUserDao) {
		this.ecUserDao = ecUserDao;
	}
	
	public ValidPermissionForUmsService getValidPermissionForUmsService() {
		return validPermissionForUmsService;
	}
	public void setValidPermissionForUmsService(
			ValidPermissionForUmsService validPermissionForUmsService) {
		this.validPermissionForUmsService = validPermissionForUmsService;
	}

	public EcGroupDao getEcGroupDao() {
		return ecGroupDao;
	}

	public void setEcGroupDao(EcGroupDao ecGroupDao) {
		this.ecGroupDao = ecGroupDao;
	}

	public ConfigBean getConfigBean() {
		return configBean;
	}

	public void setConfigBean(ConfigBean configBean) {
		this.configBean = configBean;
	}
	
	
}
