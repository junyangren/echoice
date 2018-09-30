package org.echoice.ums.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.echoice.modules.web.controller.SpringBaseController;
import org.echoice.ums.dao.EcObjectsDao;
import org.echoice.ums.domain.EcObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/opExtJs.do")
public class ExtJsController extends SpringBaseController {
	@Autowired
	private EcObjectsDao ecObjectsDao;
	@Override
	@RequestMapping(params={"action=index"})
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@RequestMapping(params={"action=accordionMenu"})
	public ModelAndView accordionMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/javascript; charset=UTF-8");
		EcObjects ecObjectsParent=ecObjectsDao.getObjectsByAlias("ecums-Manager-Menu");
		List<EcObjects> list=ecObjectsDao.findChildObjects(ecObjectsParent.getObjId());
		String content="";
		content="var itemsPaneMenu=[];";
		int i=0;
		for (EcObjects ecObjects : list) {
			content+="itemsPaneMenu["+i+"] = new Ext.Panel({"+
            "title: '"+ecObjects.getName()+"',";
		     if(i==0){
		    	 content+="items:mytree});";
		     }else if(i==1){
		    	 content+="items:mytree});";
		     }else{
		    	 content+="html: '&lt;empty panel&gt;'"+
		            "});";
		     }
		     i++;
		}
		response.getWriter().write(content);
		return null;
	}
	
	public EcObjectsDao getEcObjectsDao() {
		return ecObjectsDao;
	}
	public void setEcObjectsDao(EcObjectsDao ecObjectsDao) {
		this.ecObjectsDao = ecObjectsDao;
	}

	
}
