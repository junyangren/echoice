package org.echoice.modules.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XssJsoupHttpServletRequestWrapper extends HttpServletRequestWrapper{
	private static final Logger logger=LoggerFactory.getLogger(XssJsoupHttpServletRequestWrapper.class);
	private int filterType=1;
	public XssJsoupHttpServletRequestWrapper(HttpServletRequest request,int filterType) {
		super(request);
		// TODO Auto-generated constructor stub
		this.filterType=filterType;
	}

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		String value = super.getParameter(name);
		if(isNotBlank(value)){
			value=dealFilter( value );
		}
		return value;
	}
	
	@Override
	public String[] getParameterValues(String name) {
		String[] parameters = super.getParameterValues(name);
		if (parameters == null || parameters.length == 0) {
			return null;
		}
		for (int i = 0; i < parameters.length; i++) {
			if(isNotBlank(parameters[i])){
				parameters[i] = dealFilter(parameters[i]);
			}
			
		}
		return parameters;
	}
	
	@Override
	public String getQueryString() {
		String value = super.getQueryString();
		if (isNotBlank(value)) {
			value = dealFilter(value);
		}
		return value;
	}
	
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value != null) {
			value = dealFilter(value);
		}
		return value;
	}
	
	private String dealFilter(String value){
		String reval=value;
		switch (this.filterType) {
		case 1:
			reval=Jsoup.clean(value, Whitelist.simpleText());
			break;
		case 2:
			reval=Jsoup.clean(value, Whitelist.basic());
			break;			
		case 3:
			reval=Jsoup.clean(value, Whitelist.basicWithImages());
			break;
		default:
			break;
		}
		logger.debug("this.filterType:"+this.filterType+",value:"+reval);
		return reval;
	}
	
	private boolean isNotBlank(String value){
		if(value==null||"".equals(value)){
			return false;
		}
		return true;
	}
}
