<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>    
    <title>业务管理系统</title>
	<%@ include file="/commons/meta-app.jsp" %>
	<%@ include file="/commons/meta-easyui.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx }/static/scripts/ztree/3.5.18/css/zTreeStyle/zTreeStyle.css" />
    <script type="text/javascript" src="${ctx }/static/scripts/ztree/3.5.18/js/jquery.ztree.core-3.5.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx }/static/styles/index_layout.css" />
	<script type="text/javascript" src="${ctx }/static/scripts/apps/common.js"></script>
	<script type="text/javascript" src="${ctx }/static/scripts/apps/index.js"></script>	
  </head>
  
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="header">
		<div class="content">
			<div id="elogo" class="navbar navbar-left">
				<ul>
					<li>
						<a href="index.jsp">业务管理系统（EC-MGR）</a>
					</li>					
				</ul>
			</div>
			<div id="navbar-1" class="navbar navbar-right">
				<ul>
					<li><a href="index.jsp">首页</a></li>
					<li><a id="userCenter" href="javascript:void(0);">个人中心</a>
						<div id="userMM" class="easyui-menu" style="width:120px;">
							<div data-options="iconCls:'icon-ok'" onclick="javascript:$('#passwordLink').click();">修改密码</div>
							<div class="menu-sep"></div>
							<div data-options="iconCls:'icon-no'" onclick="javascript:top.window.location.href='logout.jsp'">注销退出</div>
						</div>
					</li>
					<li><a href="javascript:void(0);">在线帮助</a>
				</ul>
			</div>
			<div style="clear:both"></div>			
		</div>
	</div>	
	<div data-options="region:'west',split:'true',border:'true'" title="系统菜单" style="width:230px;">
		<div id="menu-accordion-id">
			<div title="业务功能">
				<ul id="menuTreeId" class="ztree"></ul>
			</div>
			<div title="个人中心">
				<a id="passwordLink" class="ec-icon" href="javascript:void(0);" title="密码修改">
					<img src="${ctx }/static/images/icons/id-card.png" />
					密码修改
				</a>			
				<a id="logout" class="ec-icon" href="logout.jsp" title="退出系统">
					<img src="${ctx }/static/images/icons/logout.png" />
					退出系统
				</a>				
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- tab -->
		<div id="tabs_center" class="easyui-tabs">
			<div id="headIndexId" title="首页">
				<form action="" target="_blank" method="post"></form>
			</div>	
		</div>
	</div>
	
</body>
</html>
