<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<%
	session.invalidate();
	String serName=request.getServerName();
	int serPort=request.getServerPort();
	String url="http://"+serName+":"+serPort+"/evc/console";
	String casServer="192.168.21.43";
	int casrPort=6080;
	response.sendRedirect("http://"+casServer+":"+casrPort+"/cas/logout?service="+url);
 %>

