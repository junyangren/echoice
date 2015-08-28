<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>信息提示页面</title>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<meta http-equiv="Cache-Control" content="no-store"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/static/styles/normalize.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/static/styles/quickLayout/quick-layout-min.css"/>" />
</head>
<body style="background-color: rgb(238,243,250);">
	<div style="width: 768px;margin:120px auto 0 auto;border:1px solid #99bbe8;background-color: #FFF">
		<div class="fix" style="padding:100px 20px">
			<div class="l">
				<img alt="error_1" src="<c:url value="/static/images/error_1.png"/>">
			</div>
			<div style="padding: 50px 0 0 0">${tipMsg}</div>
		</div>
	</div>
</body>
</html>