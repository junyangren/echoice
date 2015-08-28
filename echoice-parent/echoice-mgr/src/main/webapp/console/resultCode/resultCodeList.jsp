<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/commons/meta-app.jsp" %>
  <%@ include file="/commons/meta-easyui.jsp" %>  
  <script type="text/javascript" src="${ctx }/static/scripts/My97DatePicker/format1_WdatePicker.js"></script>
  <script type="text/javascript" src="${ctx }/static/scripts/utils/jquery.form-1.1.js"></script>
  <script type="text/javascript" src="${ctx }/static/scripts/utils/jquery.json-2.2.min.js"></script>
  <script type="text/javascript" src="${ctx }/static/scripts/utils/validator1.05.js"></script>
  <script type="text/javascript" src="${ctx }/static/scripts/apps/common.js"></script>
  <script type="text/javascript">
  <!--
  
  	(function($) {
  		$(document).ready(function(){
  			$.ecApp.resizeGridHeight({h:220,limit:false,idArr:["searchForm"]});
 			$('#tableList').datagrid();
 			
  			$('#searchBtn').click(function(){
  				//$('#tableList').datagrid('unselectAll');
  				var sfJson=$('#searchForm').getForm();
  				var rnd=parseInt(Math.random()*10000);
  				sfJson.rnd=rnd;
  				$('#tableList').datagrid('load',sfJson);
  			});
  			 
  			$('#resetBtn').click(function(){
  				$('#searchForm').form('clear');
  			});
  			
  			$('#reloadBtnID').click(function(){
  				$('#tableList').datagrid('unselectAll');
  				$('#tableList').datagrid('reload');
  			});
  			
  			$('#exportExcelBtnID').click(function(){
  				$('#searchForm').submit();
  			});
  			
  			$('#searchFormPanel input').bind('keypress',function(event){
  				if(event.keyCode==13){
  					$('#searchBtn').click();
  				}
  			});  						
	  	});
  		
  		
  	})(jQuery);	 
  	
  	var _frameTabId='${param._frameTabId}';
  			  	
	function formatOpType(value,rec){
		var tmp='<a href="javascript:void(0);" onclick="showAppInfo(\''+rec.code+'\');">'+'修改'+'</a>';
	    return tmp;	
	}
		
 	function showAppInfo(id){
   		url=context+'console/resultCode/update/'+id+'?_parentFrameTabId='+_frameTabId;
 		parent.addFrameTab({title:'错误码修改',href:url,reload:true,frameId:'vc-param-resultcode-modify-FrameID'});
 	}

	var rctoolbar = [{
		text:'新增',
		iconCls:'icon-add',
		handler:function(){
   			url=context+'console/resultCode/create?_parentFrameTabId='+_frameTabId;
   			if(parent.addFrameTab){
   				parent.addFrameTab({title:'错误码新增',href:url,reload:true,frameId:'vc-param-resultcode-add-FrameID'});
   			}else{
   				window.open(url);
   				
   			}
 			
		}
	},'-',{
		text:'删除',
		iconCls:'icon-remove',
		handler:function(){
   			var selRows=$('#tableList').datagrid('getSelections');
			if(selRows&&selRows.length>0){
				selRowsIdArr=[];
				$.each(selRows,function(index){
					selRowsIdArr[index]=this.code;
				});
				var sfJson={};
				var rnd=parseInt(Math.random()*10000);
				sfJson.rnd=rnd;
				sfJson.selIds=$.toJSON(selRowsIdArr);
  				$.post("../resultCode/delete",sfJson,function(resp){
  				    $.messager.alert('操作提示',resp.msg,'info');
  					if(resp.code==0){
  						 $('#reloadBtnID').click();
  					}
  				},"json");				
			}  			
		}
	}];
	-->
  </script>
  </head>
  <body>
  	<div class="header-navbar">当前位置：参数配置-&gt;错误码配置</div>    	
  	<div class="ec-mlr-6">
	  	<div style="margin-top: 12px;">
	  		<form id="searchForm" name="searchForm" action="" method="post" target="_blank">	  			
	  			<table class="ec-search-form">
	  				<tr>
	  					<td class="ec-form-header">错误码：</td>
	  					<td><input class="easyui-textbox" name="search_EQ_code" type="text" value="" /></td>
	  					<td class="ec-form-header">转换码：</td>
	  					<td><input class="easyui-textbox" name="search_EQ_type" type="text" value="" /></td>
	  					<td class="ec-form-header">错误信息：</td>
	  					<td><input class="easyui-textbox" name="search_LIKE_errmessages" type="text" value="" /></td>
	  				</tr>
	  				<tr>
	  					<td class="ec-form-header"></td>	
	  					<td colspan="5">
	  						<button id="searchBtn" type="button" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:60px">查询</button>
	  						<button id="resetBtn" type="button" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:60px">重置</button>
	  						<button id="reloadBtnID" type="button" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="width:60px;">刷新</button>
	  						
	  						<button id="exportExcelBtnID" type="button" class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:80px;">同步ZK</button>
	  						<%--
	  						<button id="printBtnID" type="button" class="easyui-linkbutton" data-options="iconCls:'icon-print'">打印</button>--%>
	  					</td>	  				
	  				</tr>
	  			</table>
	  		</form>
	  	</div>
	  	<div id="tableListDiv" style="width: 100%;height: 380px;padding-top: 6px">
			<table id="tableList" title="错误码查询列表"
					data-options="
						fit:true,
						fitColumns:false,
						idField:'code',
						singleSelect: true,
						selectOnCheck:true,
						collapsible: false,
						rownumbers: false,
						url: '${ctx }/console/resultCode/searchJSON',
						method: 'post',
						pagination:true,
						pageSize:20,
						toolbar:rctoolbar,
						onBeforeLoad:function(parma){
							$('#tableList').datagrid('unselectAll');
						}
					">
				<thead data-options="frozen:true">
					<tr>
						<th data-options="field:'codeCheck',checkbox:true">选择</th>
						<th data-options="field:'opType',width:60,align:'center',formatter:formatOpType">操作</th>
						<th data-options="field:'code',width:80,align:'center'">错误码</th>
						<th data-options="field:'type',width:80,align:'center'">转换码</th>

					</tr>
				</thead>			
				<thead>
					<tr>
						<th data-options="field:'note',width:350">错误描述</th>
						<th data-options="field:'errmessages',width:350">提示用户信息</th>
					</tr>
				</thead>
			</table>
	    </div>
    </div>  
  </body>
</html>
