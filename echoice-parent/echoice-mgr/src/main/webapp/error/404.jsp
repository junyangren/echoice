<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%response.setStatus(200);%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<meta name="description" content="" />
	    <meta name="keywords" content="" />
    	<title>404 Page</title>
    	<style>
    		html, body{
    			margin: 0;
    			padding: 0;
    		}
    		body{
    			color: #666;
    			text-align: center;
    			font-family: "Microsoft Yahei",arial,sans-serif;
    		}
    		h1,p{
    			margin: 0;
    		}
    		img{
    			margin-top: 120px;
    			display: inline-block;
    		}
			h1{
				font-size: 38px;
				margin-top: 50px;
				font-weight: normal;
				margin-bottom: 10px;
			}
			p{
				font-size: 19px;
				margin-top: 10px;
			}
			a {
				border: 1px solid #2773bf;
				background-color: #358ae2;
				color: #fff;
				width: 128px;
				height: 38px;
				line-height: 38px;
				font-size: 17px;
				display: inline-block;
				text-align: center;
				cursor: pointer;
				text-decoration: none;
				margin-top: 20px;
			}
			a:hover{
				background: #2171c2;
			}
    	</style>
	</head>
	<body>
		<img src="<c:url value="/static/images/404.png" />" alt="404图片">
		<h1>HTTP 404 Page Not Found</h1>
		<p>您正在访问的页面可能已经删除、更名或暂时不可用</p>
		<a href="<c:url value="/" />">返回首页</a>
	</body>
</html>