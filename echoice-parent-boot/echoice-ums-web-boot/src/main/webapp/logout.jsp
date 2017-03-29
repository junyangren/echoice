<%@page import="org.echoice.ums.util.CasUmsUtil"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<%
	session.invalidate();
	if(CasUmsUtil.isAuthCas(request)){
		String serName=request.getServerName();
		int serPort=request.getServerPort();
		String url="http://"+serName+":"+serPort+"/ecums";	
		String casSerName="serName";
		int casSerPort=serPort;
		response.sendRedirect("http://"+casSerName+":"+casSerPort+"/cas/logout?service="+url);
	}else{
		response.sendRedirect("login.jsp");
	}
 %>

