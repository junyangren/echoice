package org.echoice.modules.web;

public class MsgTip {
	private int code=0;
	private String msg="操作成功";
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return this.msg;
	}
	
}
