package org.echoice.modules.web.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.beanutils.BeanUtils;
import org.echoice.modules.web.json.bean.ExtJsActionView;
import org.echoice.modules.web.json.bean.JSONTreeNode;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;

public class ExtJsUtil {
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	public String createExtJsTree(List treeAllParentNodeList,List treeChildNodeList,String fieldNameTag,String fieldIdTag,String idStartFlag) throws Exception{		
		StringBuffer bf=new StringBuffer();
		bf.append("|");
		for (Object object : treeAllParentNodeList) {
			Long temp=(Long)object;
			bf.append(temp);
			bf.append("|");
		}
		String strParentTree=bf.toString();
		List<JSONTreeNode> listTree=new ArrayList<JSONTreeNode>();
		String nodeId="";
		String nodeName="";
		for (Object object : treeChildNodeList) {
			JSONTreeNode treeNode=new JSONTreeNode();
			nodeId=BeanUtils.getProperty(object, fieldIdTag);
			nodeName=BeanUtils.getProperty(object, fieldNameTag);
			treeNode.setId(idStartFlag+fieldIdTag);
			treeNode.setText(nodeName);
			if(strParentTree.indexOf("|"+nodeId)!=-1){
				treeNode.setLeaf(false);
			}else{
				treeNode.setLeaf(true);
			}
			listTree.add(treeNode);
		}
		//JSONArray jsonarr=JSONArray.fromObject(listTree);
		String data=JSON.toJSONString(listTree);
		return data;
	}
	/**
	 * 将前面封装的JSON ID数组转换JAVA LONG数组
	 * @param obj
	 * @return
	 */
	public static Long[] transJsonIDArrayToLong(HttpServletRequest request,String idTag){
		//JSONArray jsonArray=null;
		String ids=request.getParameter(idTag);
		//jsonArray=JSONArray.fromObject(ids);
		//Object obj[]=jsonArray.toArray();

		Object obj[]=JSON.parseArray(ids).toArray();
		Long[] destArr=new Long[obj.length];
		for (int i = 0; i < obj.length; i++) {
			destArr[i]=new Long((Integer)obj[i]);
		}
		return destArr;
	}
	
	public static void rendTextExtjs(HttpServletResponse response, String content) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/javascript;charset=UTF-8");
        response.getWriter().write(content);
    }
	/**
	 * 得到单一表单数据
	 * @param response
	 * @param obj
	 * @param excudeField
	 */
	public static void renderExtjsObject(HttpServletResponse response,Object obj,String[] excudeField) throws IOException{
		StringBuffer bf=new StringBuffer();
		bf.append("{");
		bf.append("success: true,");
		bf.append("data:");
		if(obj!=null){
			/**
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
			if(excudeField!=null){
				jsonConfig.setExcludes(excudeField);
			}
			JSONObject jsObject=JSONObject.fromObject(obj,jsonConfig);
			bf.append(jsObject.toString());**/
			
			String dataJson=JSON.toJSONString(obj,SerializeConfig.globalInstance,new SerializeFilter[]{new AppPropertyPreFilter(excudeField)},DEFAULT_DATE_PATTERN,JSON.DEFAULT_GENERATE_FEATURE);
			bf.append(dataJson);
		}else{
			bf.append("{}");
		}
		bf.append("}");
		rendTextExtjs(response, bf.toString());
	}
	/**
	 * 得到列表数据
	 * @param response
	 * @param list
	 * @param excudeField
	 */
	public static void renderExtjsList(HttpServletResponse response,List list,int total,String[] excudeField) throws IOException{
		StringBuffer bf=new StringBuffer();
		bf.append("{");
		bf.append("totalCount:"+total);
		bf.append(",data:");
		if(total!=0){
			/**
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
			if(excudeField!=null){
				jsonConfig.setExcludes(excudeField);
			}
			JSONArray jSONArray=JSONArray.fromObject(list,jsonConfig);
			bf.append(jSONArray.toString());**/
			
			String dataJson=JSON.toJSONString(list,SerializeConfig.globalInstance,new SerializeFilter[]{new AppPropertyPreFilter(excudeField)},DEFAULT_DATE_PATTERN,JSON.DEFAULT_GENERATE_FEATURE);
			bf.append(dataJson);
		}else{
			bf.append("[]");
		}
		bf.append("}");
		rendTextExtjs(response, bf.toString());
	}
	
	/**
	 * 返回表单处理对象
	 * @param response
	 * @param extjsActionView
	 * @throws IOException
	 */
	public static void renderExtjsActionView(HttpServletResponse response,ExtJsActionView extjsActionView) throws IOException{
		//JSONObject jsObject=JSONObject.fromObject(extjsActionView);
		String str=JSON.toJSONString(extjsActionView);
		rendTextExtjs(response, str);
	}
	
	public static void main(String[] args) {
		ExtJsActionView actionView=new ExtJsActionView();
		actionView.addErrorCodeMsg("aa", "bb");
		//JSONObject aSONObject=JSONObject.fromObject(actionView);
		//System.out.println(aSONObject.toString());
		
	}
}
