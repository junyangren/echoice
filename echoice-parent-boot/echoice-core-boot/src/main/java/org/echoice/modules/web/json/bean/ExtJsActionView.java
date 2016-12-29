package org.echoice.modules.web.json.bean;

import java.util.Map;
import java.util.HashMap;

public class ExtJsActionView {
	private boolean success=true;
	private Map<String, String> errorsMap=new HashMap();
	private Map<String, String> dataMap=new HashMap();
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Map<String, String> getErrorsMap() {
		return errorsMap;
	}
	public void setErrorsMap(Map<String, String> errorsMap) {
		this.errorsMap = errorsMap;
	}
	
	public Map<String, String> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}
	
	
	public void addErrorCodeMsg(String code,String msg){
		errorsMap.put(code, msg);
	};
	
	public void addDataCodeMsg(String code,String msg){
		dataMap.put(code, msg);
	};
}
