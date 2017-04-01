package org.echoice.ums.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.echoice.modules.web.json.bean.ExtJsActionView;
import org.echoice.modules.web.json.bean.JSONCheckTreeNode;
import org.echoice.modules.web.json.bean.JSONTreeNode;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.config.ConfigConstants;
import org.echoice.ums.config.LoginAuthBean;
import org.echoice.ums.dao.EcGroupDao;
import org.echoice.ums.dao.EcObjectsDao;
import org.echoice.ums.dao.EcUserDao;
import org.echoice.ums.domain.EcGroup;
import org.echoice.ums.domain.EcObjects;
import org.echoice.ums.plugins.Command;
import org.echoice.ums.plugins.bean.ResultMsg;
import org.echoice.ums.quartz.QuartzTriggerRunner;
import org.echoice.ums.service.AppPluginService;
import org.echoice.ums.service.UmsCommonService;
import org.echoice.ums.util.CasUmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

//import net.sf.json.JSONArray;

@Controller
@RequestMapping("/opGroup.do")
public class GroupController extends UmsBaseController {
	@Autowired
	private EcGroupDao ecGroupDao;
	@Autowired
	private EcUserDao ecUserDao;
	@Autowired
	private LoginAuthBean loginAuthBean;
	@Autowired
	private AppPluginService appPluginService;
	@Autowired
	private UmsCommonService umsCommonService;
	private EcObjectsDao ecObjectsDao;
	@Override
	@RequestMapping(params={"action=index"})
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		EcGroup ecGroup=new EcGroup();
		bindObject(request, ecGroup);
		int extjsPage[]=getExtJsPage(request);
		boolean isAdmin=CasUmsUtil.isAdmin(request);
		if(!isAdmin){
			if(ecGroup.getParentId()==-1){
				ecGroup.setParentId(new Long(-999999999));
			}
		}
		PageBean pageBean=ecGroupDao.findPageCondition(ecGroup, extjsPage[0], extjsPage[1]);
		renderExtjsList(response, pageBean.getDataList(), pageBean.getTotalSize(), new String[]{"ecUserGroups","ecGroupAssignments"});
		return null;
	}
	@RequestMapping(params={"action=updateGroupFullName"})
	public ModelAndView updateGroupFullName(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String groupId=request.getParameter("groupId");
		String msg="用户组Path操作成功！！";
		try{
			if(StringUtils.isBlank(groupId)){
				EcObjects objects= ecObjectsDao.getObjectsByAlias(ConfigConstants.UMS_GROUP_ROOT_ID);
				if(objects!=null&&StringUtils.isNotBlank(objects.getNote())){
					groupId=objects.getNote();
				}else{
					groupId="-1";
				}
			}
			int result=ecGroupDao.updateGroupFullNameByProc(Integer.parseInt(groupId));
			if(result!=0){
				msg="操作失败，错误码："+result;
			}
		}catch (Exception e) {
			// TODO: handle exception
			msg="操作失败，原因："+e.getMessage();
		}
		rendText(response, msg);
		return null;
	}
	
	@RequestMapping(params={"action=list"})
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return null;
	}

	/**
	 * 得到树型对象树
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params={"action=tree"})
	public ModelAndView tree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String parentId=request.getParameter("parentId");
		List list=ecGroupDao.findGroupTreeParent();
		StringBuffer bf=new StringBuffer();
		bf.append("|");
		for (Object object : list) {
			Long temp=(Long)object;
			bf.append(temp);
			bf.append("|");
		}
		String strParentTree=bf.toString();
		List<EcGroup> childList = getChildGroupList(request, parentId);
		
		List<JSONTreeNode> listTree=new ArrayList<JSONTreeNode>();
		for (EcGroup ecGroup : childList) {
			JSONTreeNode treeNode=new JSONTreeNode();
			treeNode.setId(ConfigConstants.GROUP_TREE+ecGroup.getGroupId());
			treeNode.setText(ecGroup.getName());
			if(strParentTree.indexOf("|"+ecGroup.getGroupId()+"|")!=-1){
				treeNode.setLeaf(false);
			}else{
				treeNode.setLeaf(true);
			}
			listTree.add(treeNode);
		}
		//JSONArray jsonarr=JSONArray.fromObject(listTree);
		String data=JSON.toJSONString(listTree);
		rendTextExtjs(response, data);
		return null;
	}
	
	/**
	 * 得到树型对象树
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params={"action=treeAssignGroup"})
	public ModelAndView treeAssignGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String parentId=request.getParameter("parentId");
		String userId=request.getParameter("userId");
		List list=ecGroupDao.findGroupTreeParent();
		StringBuffer bf=new StringBuffer();
		bf.append("|");
		for (Object object : list) {
			Long temp=(Long)object;
			bf.append(temp);
			bf.append("|");
		}
		String strParentTree=bf.toString();
		StringBuffer bf2=new StringBuffer();
		
		if(StringUtils.isNotBlank(userId)){
			bf2.append("|");
			List groupList=ecUserDao.findGroupByUserId(Long.valueOf(userId));
			for (Object object : groupList) {
				EcGroup ecGroup=(EcGroup)object;
				bf2.append(ecGroup.getGroupId());
				bf2.append("|");
			}
		}
		
		String checkTreeStr=bf2.toString();
		
		List<EcGroup> childList = getChildGroupList(request, parentId);
		List<JSONCheckTreeNode> listTree=new ArrayList<JSONCheckTreeNode>();
		for (EcGroup ecGroup : childList) {
			JSONCheckTreeNode treeNode=new JSONCheckTreeNode();
			treeNode.setId(ConfigConstants.GROUP_TREE+ecGroup.getGroupId());
			treeNode.setText(ecGroup.getName());
			if(strParentTree.indexOf("|"+ecGroup.getGroupId()+"|")!=-1){
				treeNode.setLeaf(false);
			}else{
				treeNode.setLeaf(true);
			}
			//选中已经分配的组
			if(StringUtils.isNotBlank(userId)){
				if(checkTreeStr.indexOf("|"+ecGroup.getGroupId()+"|")!=-1){
					treeNode.setChecked(true);
				}else{
					treeNode.setChecked(false);
				}	
			}
			listTree.add(treeNode);
		}
		
		//JSONArray jsonarr=JSONArray.fromObject(listTree);
		//rendTextExtjs(response, jsonarr.toString());
		
		String data=JSON.toJSONString(listTree);
		rendTextExtjs(response, data);
		return null;
	}
	/**
	 * 得到子节点用户组列表
	 * @param request
	 * @param parentId
	 * @return
	 */
	@RequestMapping(params={"action=getChildGroupList"})
	private List<EcGroup> getChildGroupList(HttpServletRequest request,
			String parentId) {
		boolean isUserGoup=true;
		if(StringUtils.isNotBlank(parentId)&&"-1".equals(parentId)){
			boolean isAdmin=CasUmsUtil.isAdmin(request);
			if(isAdmin){
				isUserGoup=false;
			}
		}else{
			isUserGoup=false;
		}
		List<EcGroup> childList=null;
		if(isUserGoup){
			//取得用户所在用户组列表
			if(loginAuthBean.getGroupModeType()==1){
				//childList=ecUserDao.findGroupByUserAlias(CasUmsUtil.getUser(request));
				childList=new ArrayList<EcGroup>();
				childList.add(CasUmsUtil.getUserGroup(request));
			}else{
				childList=ecUserDao.findGroupByUserAndParenRoleAlias(CasUmsUtil.getUser(request),loginAuthBean.getGroupRoleParentAlias());
			}
			//
		}else{
			childList=ecGroupDao.findGroupTreeChild(Long.valueOf(parentId));
		}
		return childList;
	}
	
	@RequestMapping(params={"action=save"})
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EcGroup ecGroup=new EcGroup();
		bindObject(request, ecGroup);
		ecGroup.setAlias(ecGroup.getAlias().trim());
		ecGroup.setName(ecGroup.getName().trim());
		ExtJsActionView actionView=new ExtJsActionView(); 
		actionView.setSuccess(true);
		boolean isNew=false;
		if(ecGroup.getGroupId()==null){
			isNew=true;
			actionView.addDataCodeMsg("leaf", "true");
			if(StringUtils.isNotBlank(ecGroup.getAlias())){
				List list=ecGroupDao.findByAlias(ecGroup.getAlias());
				if(list!=null&&list.size()>0){
					actionView.addErrorCodeMsg("msg", "对不起，用户组标识"+ecGroup.getAlias()+"已经存在，请换一个");
					renderExtjsActionView(response, actionView);
					return null;
				}
			}
			
		}else{
			ecGroup.setAlias(ecGroup.getAlias().trim());
			List<EcGroup> list=ecGroupDao.findByAlias(ecGroup.getAlias());
			if(list!=null&&list.size()>0){
				EcGroup tmp=list.get(0);
				if(tmp.getGroupId().longValue()!=ecGroup.getGroupId().longValue()){
					actionView.addErrorCodeMsg("msg", "对不起，用户组标识"+ecGroup.getAlias()+"已经存在，请换一个");
					renderExtjsActionView(response, actionView);
					return null;
				}
			}
		}
		actionView.addDataCodeMsg("isAdd", "true");
		//对于普通管理员当parentId为-1时，取所在的根节点
		boolean isAdmin=CasUmsUtil.isAdmin(request);
		if(!isAdmin){
			if(ecGroup.getParentId()!=null&&ecGroup.getParentId().intValue()==-1){
				EcGroup parneGroup=CasUmsUtil.getUserGroup(request);
				ecGroup.setParentId(parneGroup.getGroupId());
				actionView.addDataCodeMsg("isAdd", "false");
			}
		}
		
		umsCommonService.saveGroup(ecGroup);
		if(loginAuthBean.getSyncGroupPath()){
			QuartzTriggerRunner.runGroupSyncTask();
		}
		if(isNew){
			logger.info(CasUmsUtil.getUser(request)+" 新增用户组："+ecGroup.getAlias()+"，"+ecGroup.getName());
		}else{
			logger.info(CasUmsUtil.getUser(request)+" 修改用户组："+ecGroup.getAlias()+"，"+ecGroup.getName());
		}
		actionView.addDataCodeMsg("id", ConfigConstants.GROUP_TREE+ecGroup.getGroupId());
		actionView.addDataCodeMsg("text", ecGroup.getName());
		
		renderExtjsActionView(response, actionView);
		return null;
	}
	
	@RequestMapping(params={"action=edit"})
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		EcGroup ecGroup=new EcGroup();
		bindObject(request, ecGroup);	
		ecGroup=getEcGroupDao().findOne(ecGroup.getGroupId());
		
		renderExtjsObject(response, ecGroup, new String[]{"ecUserGroups","ecGroupAssignments"});
		return null;
	}
	
	@RequestMapping(params={"action=del"})
	public ModelAndView del(HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		String ids=request.getParameter("ids");
		//判断是不否存在子节点
		//JSONArray jsonArray=JSONArray.fromObject(ids);
		//Object idsArr[]=jsonArray.toArray();
		Object idsArr[]=JSON.parseArray(ids).toArray();
		//过滤器判断
		String idsStr=StringUtils.join(idsArr,",");
		ResultMsg resultMsg=null;
		List<Command<ResultMsg, String>> groupFilterList=appPluginService.getGroupFilterList();
		if(groupFilterList!=null&&groupFilterList.size()>0){
			for (Command<ResultMsg, String> tmp : groupFilterList) {
				resultMsg=tmp.execute(idsStr);
				if(!resultMsg.isResult()){
					rendText(response, resultMsg.getMsg());
					return null;
				}
			}
		}

		List<EcGroup> groupList=ecGroupDao.findGroupListByIds(idsStr);
		
		ecGroupDao.deleteGroupByIds(idsStr);
		
		for (EcGroup ecGroup : groupList) {
			logger.info(CasUmsUtil.getUser(request)+" 删除用户组："+ecGroup.getAlias()+"，"+ecGroup.getName());
		}
		return null;
	}
	
	@RequestMapping(params={"action=asynTree"})
	public ModelAndView asynTree(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return null;
	}
	
	/**
	 * 节点拖动
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params={"action=drag"})
	public ModelAndView drag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String dragId=request.getParameter("dragId");
		String targetId=request.getParameter("targetId");
		umsCommonService.saveDragGroup(Long.valueOf(dragId), Long.valueOf(targetId));
		if(loginAuthBean.getSyncGroupPath()){
			QuartzTriggerRunner.runGroupSyncTask();
		}
		return null;
	}
	
	public EcGroupDao getEcGroupDao() {
		return ecGroupDao;
	}
	public void setEcGroupDao(EcGroupDao ecGroupDao) {
		this.ecGroupDao = ecGroupDao;
	}

	public EcUserDao getEcUserDao() {
		return ecUserDao;
	}

	public void setEcUserDao(EcUserDao ecUserDao) {
		this.ecUserDao = ecUserDao;
	}

	public LoginAuthBean getLoginAuthBean() {
		return loginAuthBean;
	}

	public void setLoginAuthBean(LoginAuthBean loginAuthBean) {
		this.loginAuthBean = loginAuthBean;
	}


	public AppPluginService getAppPluginService() {
		return appPluginService;
	}
	public void setAppPluginService(AppPluginService appPluginService) {
		this.appPluginService = appPluginService;
	}
	public UmsCommonService getUmsCommonService() {
		return umsCommonService;
	}

	public void setUmsCommonService(UmsCommonService umsCommonService) {
		this.umsCommonService = umsCommonService;
	}

	public EcObjectsDao getEcObjectsDao() {
		return ecObjectsDao;
	}

	public void setEcObjectsDao(EcObjectsDao ecObjectsDao) {
		this.ecObjectsDao = ecObjectsDao;
	}

	
}
