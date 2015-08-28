<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  <%@ include file="/commons/meta-app.jsp" %>
  <%@ include file="/commons/meta-easyui.jsp" %>
  <link rel="stylesheet" type="text/css" href="${ctx }/static/styles/quickLayout/quick-layout-min.css" />
  <style type="text/css">
  <!--
  	.header{
  		text-align: right;
  		width: 150px;
  	}
  -->
  </style>
  <script type="text/javascript" src="${ctx }/static/scripts/utils/jquery.form-1.0.js"></script>
  <script type="text/javascript" src="${ctx }/static/scripts/utils/jquery.json-2.2.min.js"></script>
  <script type="text/javascript" src="${ctx }/static/scripts/utils/validator1.05.js"></script>
    <script type="text/javascript">
    <!--
    	$(document).ready(function(){
			$('#subBtn').click(function(){
				var dForm=document.forms[0];
				var isValid=Validator.Validate(dForm,3);
				if(isValid){
					//取数据
					var v=$('#newPasswordID').val();
					//var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
					//var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$", "g");
					var strongRegex = new RegExp("^(?=.{6,})(?=.*[a-z])(?=.*[0-9]).*$", "g");
					if(!strongRegex.test(v)){
						//$.messager.alert('信息提示','密码至少8位，包含大小字母、数字组成！','info');
						$.messager.alert('信息提示','密码至少6位，由字母、数字组成！','info');
						return;
					}			
					var sfJson=$('#updatePasswordForm').getForm();
					$('#showLoading').showLoading();
					sfJson=$.extend(sfJson,{action:'updatePassword',rnd:Math.random()});
					$.post('app/opUms.do',sfJson,function(resp){
						$('#showLoading').hideLoading();
						var msg='密码修改成功！';
						if(resp.code!=0){
							msg='密码修改失败！';
						}else{
							dForm.reset();
						}			
						$.messager.alert('信息提示',msg,'info');
					},'json');					
				}				
			})
    	});
    -->
    </script>
  </head>
  <body>
  	<div class="header-navbar">当前位置：个人中心->密码修改</div>
  	<div style="text-align: center;" id="showLoading">
  		<div style="width: 600px;margin: auto;text-align: left;">
  			<form id="updatePasswordForm" name="updatePasswordForm" action="" method="post">
  				<table class="ec-table" style="margin-top: 12px;width: 100%;">
  					<tr>
		  				<td class="header" style="width: 150px;text-align: right;">旧密码：</td><td><input name="oldPassword" type="password" dataType="Require" msg="必填"/></td>
		  			</tr>
		  			<tr>
		  				<td class="header">新密码：</td><td><input id="newPasswordID" name="newPassword" type="password" dataType="Limit" min="6" msg="密码至少6位" /></td>
		  			</tr>
		  			<tr>
		  				<td class="header">确认密码：</td><td><input name="confirmPassword" type="password" dataType="Repeat" to="newPassword" msg="两次输入的密码不一致" /></td>
		  			</tr>
		  			<tr>
		  				<td class="header"></td><td><button id="subBtn" type="button" class="btn btn-olive lh14">提交</button></td>
		  			</tr>
		  		</table>
		  	</form>		
	     </div>   
	</div>
  </body>
</html>
