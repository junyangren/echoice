package org.echoice.ums.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.echoice.modules.encrypt.MD5;
import org.echoice.modules.web.json.bean.ExtJsActionView;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.dao.EcGroupDao;
import org.echoice.ums.dao.EcUserDao;
import org.echoice.ums.domain.EcUser;
import org.echoice.ums.domain.EcUserExtend;
import org.echoice.ums.plugins.Command;
import org.echoice.ums.plugins.bean.ResultMsg;
import org.echoice.ums.service.AppPluginService;
import org.echoice.ums.service.UmsCommonService;
import org.echoice.ums.util.CasUmsUtil;
import org.echoice.ums.web.view.EcUserInfoView;
import org.echoice.ums.web.view.EcUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
@Controller
@RequestMapping("/opUser.do")
public class UserController extends UmsBaseController {
	@Autowired
	private EcUserDao ecUserDao;
	@Autowired
	private EcGroupDao ecGroupDao;
	@Autowired
	private UmsCommonService umsCommonService;
	@Autowired
	private AppPluginService appPluginService;
	@Override
	@RequestMapping(params={"action=index"})
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		EcUserView ecUser=new EcUserView();
		bindObject(request, ecUser);
		if(ecUser.getGroupId()==null){
			ecUser.setGroupId(new Long(-1));
		}
		int extjsPage[]=getExtJsPage(request);
		PageBean pageBean=ecUserDao.findPageCondition(ecUser, extjsPage[0], extjsPage[1]);
		renderExtjsList(response, pageBean.getDataList(), pageBean.getTotalSize(), new String[]{"ecUserGroups","ecUserExtends","ecUsersAssignmens"});
		return null;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params={"action=search"})
	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		EcUserView ecUser=new EcUserView();
		bindObject(request, ecUser);
		int extjsPage[]=getExtJsPage(request);
		PageBean pageBean=null;
		boolean isAdmin=CasUmsUtil.isAdmin(request);
		if(!isAdmin){
			ecUser.setGroupId(CasUmsUtil.getUserGroup(request).getGroupId());
			pageBean=ecUserDao.searchPageConditionSQL(ecUser, extjsPage[0], extjsPage[1]);
		}else{
			pageBean=ecUserDao.searchPageCondition(ecUser, extjsPage[0], extjsPage[1]);
		}
		
		renderExtjsList(response, pageBean.getDataList(), pageBean.getTotalSize(), new String[]{"ecUserGroups","ecUsersAssignmens"});
		return null;
	}
	@RequestMapping(params={"action=save"})
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response)throws Exception {
		ExtJsActionView actionView=new ExtJsActionView(); 
		actionView.setSuccess(true);
		//String groupId=request.getParameter("groupId");
		String groupIds=request.getParameter("groupIds");
		String groupIdsArr[]=StringUtils.split(groupIds, ",");
		EcUser ecUser=new EcUser();
		bindObject(request, ecUser);
		EcUserExtend ecUserExtend=new EcUserExtend();
		bindObject(request, ecUserExtend);
		ecUser.setAlias(ecUser.getAlias().trim());
		ecUser.setName(ecUser.getName().trim());
		//密码加密
		String password=ecUser.getAlias()+ecUser.getPassword();
		MD5 md5=new MD5();
		String md5Password=md5.getMD5ofStr(password);
		if(ecUser.getUserId()==null){
			List list=ecUserDao.findByAlias(ecUser.getAlias());
			if(list!=null&&list.size()>0){
				actionView.addErrorCodeMsg("msg", "对不起，用户登入名"+ecUser.getAlias()+"已经存在，请换一个");
				renderExtjsActionView(response, actionView);
				return null;
			}
			ecUser.setPassword(md5Password);
			logger.info(CasUmsUtil.getUser(request)+" 新增用户："+ecUser.getAlias()+"，"+ecUser.getName());
		}else{
			EcUser ecUser2=ecUserDao.findOne(ecUser.getUserId());
			//密码加密
			if(!(ecUser2.getPassword().equals(ecUser.getPassword()))){
				ecUser.setPassword(md5Password);
			}
			//Log4jMsgBean bean=new Log4jMsgBean();
			logger.info(CasUmsUtil.getUser(request)+" 修改用户："+ecUser.getAlias()+"，"+ecUser.getName());
			//logger.info(bean);
		}
		ecUser.setOpTime(new Date());
		umsCommonService.saveUserData(ecUser, ecUserExtend, groupIdsArr);		
		renderExtjsActionView(response, actionView);
		return null;
	}
	@RequestMapping(params={"action=edit"})
	public ModelAndView edit(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		EcUser ecUser=new EcUser();
		bindObject(request, ecUser);
		EcUserInfoView ecUserInfoView=null;
		if(ecUser.getUserId()==null){
			String userName=getSessionUserName(request);
			ecUserInfoView =ecUserDao.getByAlias(userName);
		}else{
			ecUserInfoView =ecUserDao.getById(ecUser.getUserId());
		}
		ecUserInfoView.setConfirmPassword(ecUserInfoView.getPassword());
		renderExtjsObject(response, ecUserInfoView, new String[]{"ecUserGroups","ecUsersAssignmens"});
		return null;
	}
	@RequestMapping(params={"action=del"})
	public ModelAndView del(HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		String ids=request.getParameter("ids");
		JSONArray jsonArray=JSONArray.fromObject(ids);
		Object idsArr[]=jsonArray.toArray();
		String idsStr=StringUtils.join(idsArr, ",");
		List<EcUser> list=getEcUserDao().findUserListByIds(idsStr);
		//
		List<Command<ResultMsg, String>> userFilterList=appPluginService.getUserFilterList();
		if(userFilterList!=null&&userFilterList.size()>0){
			ResultMsg resultMsg=null;
			for (Command<ResultMsg, String> cmd : userFilterList) {
				resultMsg=cmd.execute(idsStr);
				if(!resultMsg.isResult()){
					rendText(response, resultMsg.getMsg());
					return null;
				}
			}
		}
		umsCommonService.removeUsers(idsArr);
		for (EcUser ecUser : list) {
			logger.info(CasUmsUtil.getUser(request)+" 删除用户："+ecUser.getAlias()+"，"+ecUser.getName());
		}
		return null;
	}	
	
	
	public EcUserDao getEcUserDao() {
		return ecUserDao;
	}
	public void setEcUserDao(EcUserDao ecUserDao) {
		this.ecUserDao = ecUserDao;
	}
	public EcGroupDao getEcGroupDao() {
		return ecGroupDao;
	}
	public void setEcGroupDao(EcGroupDao ecGroupDao) {
		this.ecGroupDao = ecGroupDao;
	}
	public UmsCommonService getUmsCommonService() {
		return umsCommonService;
	}
	public void setUmsCommonService(UmsCommonService umsCommonService) {
		this.umsCommonService = umsCommonService;
	}
	public AppPluginService getAppPluginService() {
		return appPluginService;
	}
	public void setAppPluginService(AppPluginService appPluginService) {
		this.appPluginService = appPluginService;
	}


	
}
