package org.echoice.modules.web.json;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 * easyUI JSON数据格式生成工具
 * @author
 *
 */
public class EasyUIUtil {
	private final static String FIELD_TOTAL="total";
	private final static String FIELD_ROWS="rows";
	private final static String FIELD_FOOTER="footer";
	/**
	 * 生成easyUI,分页dataGrid数据
	 * @param pageBean
	 * @param footList
	 * @return
	 */
	public static String getGridJSON(long totalSize,List bodyList,List footList){
		String str="";
		if(bodyList!=null&&bodyList.size()>0){
			str="{\"total\":"+totalSize+","+"\"rows\":"+JSON.toJSONString(bodyList);
			if(footList!=null&&footList.size()>0){
				str+=",\"footer\":"+JSON.toJSONString(footList);
			}
			str+="}";
		}else{
			str="{\"total\":"+0+","+"\"rows\":"+"[]"+",\"footer\":[]}";
		}
		return str;
	}
	
	public static String getGridFastJSON(long totalSize,List bodyList,List footList){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put(FIELD_TOTAL, totalSize);
		if(bodyList==null){
			bodyList=new ArrayList();
		}
		jsonObject.put(FIELD_ROWS, bodyList);
		
		if(footList==null){
			footList=new ArrayList();
		}
		
		jsonObject.put(FIELD_FOOTER, footList);
		String resp=jsonObject.toJSONString();
		return resp;
	}

}
