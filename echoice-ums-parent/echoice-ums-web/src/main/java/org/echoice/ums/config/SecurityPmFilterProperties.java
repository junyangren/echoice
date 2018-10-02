package org.echoice.ums.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="ecums.security")
public class SecurityPmFilterProperties {
	private List<String> urlPatterns;
	private int order;
	public List<String> getUrlPatterns() {
		return urlPatterns;
	}
	public void setUrlPatterns(List<String> urlPatterns) {
		this.urlPatterns = urlPatterns;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
}
