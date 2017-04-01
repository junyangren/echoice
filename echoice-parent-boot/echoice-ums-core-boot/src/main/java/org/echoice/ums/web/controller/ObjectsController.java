package org.echoice.ums.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.echoice.modules.utils.DateUtil;
import org.echoice.modules.web.json.bean.ExtJsActionView;
import org.echoice.modules.web.json.bean.JSONTreeNode;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.config.ConfigConstants;
import org.echoice.ums.dao.EcObjectsDao;
import org.echoice.ums.domain.EcObjects;
import org.echoice.ums.web.view.ObjectsView;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;


@Controller
@RequestMapping("/opObjects.do")
public class ObjectsController extends UmsBaseController{
	@Autowired
	private EcObjectsDao ecObjectsDao;
	@Override
	@RequestMapping(params={"action=index"})
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		EcObjects ecObjects=new EcObjects();
		bindObject(request, ecObjects);
		int extjsPage[]=getExtJsPage(request);
		PageBean pageBean=ecObjectsDao.searchPageCondition(ecObjects, extjsPage[0], extjsPage[1]);
		renderExtjsList(response, pageBean.getDataList(), pageBean.getTotalSize(), new String[]{"ecOperators"});
		return null;
	}

	@RequestMapping(params={"action=edit"})
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		EcObjects ecObjects=new EcObjects();
		bindObject(request, ecObjects);		
		ecObjects=getEcObjectsDao().findOne(ecObjects.getObjId());
		ObjectsView objectsView=new ObjectsView();
		BeanUtils.copyProperties(objectsView, ecObjects);
		if(ecObjects.getParentId()!=null){
			EcObjects ecObjectsParent=getEcObjectsDao().findOne(ecObjects.getParentId());
			if(ecObjectsParent!=null){
				objectsView.setParentName(ecObjectsParent.getName());
			}else{
				objectsView.setParentName("对象");
			}
		}
		renderExtjsObject(response, ecObjects, new String[]{"ecOperators"});
		return null;
	}
	@RequestMapping(params={"action=del"})
	public ModelAndView del(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String ids=request.getParameter("ids");
		//JSONArray jsonArray=JSONArray.fromObject(ids);
		//Object idsArr[]=jsonArray.toArray();
		
		Object idsArr[]=JSON.parseArray(ids).toArray();
		for (int i = 0; i < idsArr.length; i++) {
			Long id=new Long((Integer)idsArr[i]);
			List list=getEcObjectsDao().findChildObjects(id);
			if(list==null||list.size()==0){
				getEcObjectsDao().delete(id);
			}
		}
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
		getEcObjectsDao().updateDrag(Long.valueOf(dragId), Long.valueOf(targetId));
		return null;
	}
	/**
	 * 保存对象数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params={"action=save"})
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ExtJsActionView actionView=new ExtJsActionView(); 
		actionView.setSuccess(true);
		EcObjects ecObjects=new EcObjects();
		bindObject(request, ecObjects);
		ecObjects.setAlias(ecObjects.getAlias().trim());
		ecObjects.setName(ecObjects.getName().trim());
		if(ecObjects.getObjId()==null){
			List list=ecObjectsDao.findByAlias(ecObjects.getAlias());
			if(list!=null&&list.size()>0){
				actionView.addErrorCodeMsg("msg", "对不起，对象标识"+ecObjects.getAlias()+"已经存在，请换一个");
				renderExtjsActionView(response, actionView,true);
				return null;
			}else{
				actionView.addDataCodeMsg("leaf", "true");
			}
			
		}
		String serverPath=getServletContext().getRealPath("/");
		serverPath+=File.separator+"uploadfiles/images/icons"+File.separator;
		File file2=new File(serverPath);
		if(!file2.exists()){
			file2.mkdirs();
		}
		String fildPath="uploadfiles/images/icons"+File.separator;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator temp=multipartRequest.getFileNames();
		while(temp.hasNext()){
			String element = (String) temp.next();
			MultipartFile file =  multipartRequest.getFile(element);
			if(StringUtils.isNotBlank(file.getOriginalFilename())){
				String dateStr=DateUtil.format(new Date(),"yyyyMMddHHmmss");
				String fileName=dateStr+"-"+file.getOriginalFilename();
				FileCopyUtils.copy(file.getBytes(), new File(serverPath+fileName));
				ecObjects.setIcon(fildPath+fileName);
			}
		}
		ecObjects.setOpTime(new Date());

		//对数据进行xss filter
		ecObjects.setAlias(Jsoup.clean(ecObjects.getAlias(), Whitelist.simpleText()));
		ecObjects.setName(Jsoup.clean(ecObjects.getName(), Whitelist.simpleText()));
		ecObjects.setNote(Jsoup.clean(ecObjects.getNote(), Whitelist.simpleText()));
		ecObjects.setNote2(Jsoup.clean(ecObjects.getNote2(), Whitelist.simpleText()));
		ecObjects.setNote3(Jsoup.clean(ecObjects.getNote3(), Whitelist.simpleText()));
		ecObjects.setStatus(Jsoup.clean(ecObjects.getStatus(), Whitelist.simpleText()));
		//end xss filter
		
		getEcObjectsDao().save(ecObjects);
		actionView.addDataCodeMsg("id", ConfigConstants.OBJECT_TREE+ecObjects.getObjId());
		actionView.addDataCodeMsg("text", ecObjects.getName());
		renderExtjsActionView(response, actionView,true);
		//renderExtjsActionView(response, actionView);
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
		List list=ecObjectsDao.findAllParent();
		StringBuffer bf=new StringBuffer();
		bf.append("|");
		for (Object object : list) {
			Long temp=(Long)object;
			bf.append(temp);
			bf.append("|");
		}
		
		String strParentTree=bf.toString();
		
		List<EcObjects> childList=ecObjectsDao.findChildObjects(Long.valueOf(parentId));
		List<JSONTreeNode> listTree=new ArrayList<JSONTreeNode>();
		for (EcObjects ecObject : childList) {
			JSONTreeNode treeNode=new JSONTreeNode();
			treeNode.setId(ConfigConstants.OBJECT_TREE+ecObject.getObjId());
			treeNode.setText(ecObject.getName());
			if(strParentTree.indexOf("|"+ecObject.getObjId()+"|")!=-1){
				treeNode.setLeaf(false);
			}else{
				treeNode.setLeaf(true);
			}
			listTree.add(treeNode);
		}
		//JSONArray jsonarr=JSONArray.fromObject(listTree);
		//rendTextExtjs(response, jsonarr.toString());
		
		String data=JSON.toJSONString(listTree);
		rendTextExtjs(response, data);
		return null;
	}
	
	public ModelAndView assginAccessMode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	public EcObjectsDao getEcObjectsDao() {
		return ecObjectsDao;
	}
	
	public void setEcObjectsDao(EcObjectsDao ecObjectsDao) {
		this.ecObjectsDao = ecObjectsDao;
	}
}
