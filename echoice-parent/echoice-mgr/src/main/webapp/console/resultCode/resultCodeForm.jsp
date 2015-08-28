<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/commons/meta-app.jsp" %> 
  <%@ include file="/commons/meta-easyui.jsp" %>
  <link rel="stylesheet" type="text/css" href="${ctx }/static/styles/quickLayout/quick-layout-min.css" />
  <script type="text/javascript" src="${ctx }/static/scripts/utils/jquery.form-1.1.js"></script>
  <script type="text/javascript" src="${ctx }/static/scripts/utils/jquery.json-2.2.min.js"></script>
  <script type="text/javascript" src="${ctx }/static/scripts/utils/validator1.05.js"></script>
  <script type="text/javascript" src="${ctx }/static/scripts/apps/common.js"></script>
  <script type="text/javascript">
  <!--
  	$.ecApp=$.ecApp||{};
  	(function($) {
  		$(document).ready(function(){
  		
  			$.ecApp.ajaxSubmitResultCode=function(){
  				var sfJson=$('#editForm').getForm();
  				var rnd=parseInt(Math.random()*1000000);
  				sfJson.rnd=rnd;
  				var vframeId='${param._parentFrameTabId }';
  				$.post("${ctx}/console/resultCode/update",sfJson,function(data){
  					$.messager.alert('操作提示',data.msg,'info');
  					if(data.code==0){
						 if(vframeId!=''){
							parent.reloadFrame({frameId:vframeId});
						 }
  					}
					
  				},"json"); 			
  			};
  			/**
  			$("#editForm").validate({
  				submitHandler:function(form){
  					$.ecApp.ajaxSubmitResultCode();
  					//return false;
  				}
  			});
  			**/
  			
  			$('#saveBtn').click(function(){
  				if(Validator.Validate(document.forms["editForm"],3)){
	  				$.ecApp.ajaxSubmitResultCode();
  				}
  				
  			});
  			
  			
  		});
  		
  	})(jQuery);	 
  	

	-->
  </script>
  </head>
  <body>
  	<div class="header-navbar">当前位置：参数配置->错误码配置</div>
  	<div class="w700 auto bdc bg-white mt20" style="overflow:auto; resize:both;">
  		<h4 class="f14 p5 pl10 bgf0 mt1 bbc">错误码编辑</h4>
  		<div class="p30">
  			<form id="editForm" name="editForm" method="post" action="">
            	<div class="fix">
                	<span class="l w120 g6 mr20 tr pt5"><span class="red fs">*</span>错误码：</span>
                    <div class="cell">
                    	<input class="pct70 p5" placeholder="错误码" name="code" value="${listone.code }" dataType="Require" msg="必填" ${listone.code==null?'':'readonly'}/>
                    </div>
                </div>
                <div class="fix mt20">
                	<span class="l w120 g6 mr20 tr pt5"><span class="red fs">*</span>错误描述：</span>
                    <div class="cell">
                    	<textarea class="pct70 p5" style="height: 36px;" name="note" dataType="Require" msg="必填">${listone.note }</textarea>
                    </div>
                </div>                
                <div class="fix mt20">
                	<span class="l w120 g6 mr20 tr pt5">转换码：</span>
                    <div class="cell">
                    	<input class="pct70 p5" placeholder="转换码" name="type" value="${listone.type }" />
                    </div>
                </div>
                
                <div class="fix mt20">
                	<span class="l w120 g6 mr20 tr pt5">提示用户信息：</span>
                    <div class="cell">
                    	<textarea class="pct70 p5" style="height: 36px;" name="errmessages">${listone.errmessages }</textarea>
                    </div>
                </div>

                <div class="fix mt20">
                	<span class="l w120 mr20">&nbsp;</span>
                	<div class="cell">
                		<button type="button" class="btn btn-red lh14" id="saveBtn">保存</button>
                    </div>
                </div>	                			
  			</form>
  		</div>
  	</div>
  </body>
</html>
