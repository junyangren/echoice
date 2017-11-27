<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/commons/meta-app.jsp" %>
  <%@ include file="/commons/easyui-css.jsp" %>
  <link rel="stylesheet" href="${r"${ctx }"}/static/component/layui/2.2.2/css/layui.css"  media="all">
  <style type="text/css">
    .ec-inline{
        position: relative;
        display: inline-block;
        vertical-align: middle;
    }
    .ec-form-label{
        float: left;
        display: block;
        padding: 9px 15px;
        width: 80px;
        font-weight: 400;
        text-align: right;
        line-height: 20px;
    }
    .ec-input-inline{
        float: left;
        width: 190px;
        margin-right: 10px;
    }
  </style>  
  </head>
  <body>
  	<div class="header-navbar">当前位置：首页->${table_annotation}</div>
  	<div class="ec-mlr-6">
	  	<div style="margin-top: 12px;">
	  		<form id="searchForm" name="searchForm" action="" method="post" target="_blank" class="layui-form">
               <div class="layui-form-item">
                <#if model_column?exists>
                    <#list model_column as model>
                    <#if (!(model.changeColumnName = 'CreateTime' ||model.changeColumnName = 'CreateUser' || model.changeColumnName = 'OpTime' || model.changeColumnName = 'OpUser')) >              
                   <div class="layui-inline">
                       <label class="layui-form-label">${model.columnShortComment!}：</label>
                       <div class="layui-input-inline">
                       <#if (model.javaType = 'Date') >
                            <input type="text" name="search_EQ_${model.changeColumnName?uncap_first}" value="" placeholder="请输入${model.columnShortComment!}" autocomplete="off" class="layui-input" readonly onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                       <#elseif (model.javaType = 'Integer'||model.javaType = 'Long'||model.javaType = 'Double') >
                            <input type="number" name="search_EQ_${model.changeColumnName?uncap_first}" autocomplete="off" class="layui-input" />
                       <#elseif (model.options ?exists) >
                            <select name="search_EQ_${model.changeColumnName?uncap_first}">
                              <option value="">请选择</option>
                              <#list model.options as oneOption>
                              <option value="${oneOption.value}">${oneOption.name}</option>
                              </#list>
                            </select>                            
                       <#else>
                            <input type="text" name="search_LIKE_${model.changeColumnName?uncap_first}" placeholder="请输入${model.columnShortComment!}" autocomplete="off" class="layui-input" />
                       </#if>
                       </div>
                   </div>
                   </#if>
                   </#list>
                </#if>
                 <div class="layui-inline">
                    <button id="searchBtn" type="button" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:60px">查询</button>
                    <button id="resetBtn" type="button" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:60px">重置</button>
                    <button id="reloadBtnID" type="button" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="width:60px;">刷新</button>
                 </div>
               </div>                       	  			
	  		</form>
	  	</div>
	  	<div id="tableListDiv" style="width: 100%;height: 380px;padding-top: 6px">
			<table id="tableList" title="查询列表"
					data-options="
						fit:true,
						fitColumns:false,
						idField:'${pkColumn.changeColumnName?uncap_first}',
						singleSelect: true,
						selectOnCheck:true,
						collapsible: false,
						rownumbers: false,
						url: '${r"${ctx }"}/console/${table_name?uncap_first}/searchJSON',
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
						<th data-options="field:'${pkColumn.changeColumnName?uncap_first}Check',checkbox:true">选择</th>
						<th data-options="field:'opType',width:60,align:'center',formatter:formatOpType">操作</th>
						<th data-options="field:'${pkColumn.changeColumnName?uncap_first}',width:80,align:'center'">${pkColumn.columnShortComment!}</th>
					</tr>
				</thead>			
				<thead>
					<tr>
					    <#if model_column?exists>
        					<#list model_column as model>
        						<#if (!model.pk) >
						<th data-options="field:'${model.changeColumnName?uncap_first}',width:120,align:'center'<#if (model.options ?exists) >,formatter:format${model.changeColumnName}</#if>">${model.columnShortComment!}</th>
								</#if>
						    </#list>
    					</#if>
					</tr>
				</thead>
			</table>
	    </div>
    </div>
  <%@ include file="/commons/common-js.jsp" %>
  <%@ include file="/commons/easyui-js.jsp" %>
  <script type="text/javascript" src="${r"${ctx }"}/static/component/layui/2.2.2/layui.js"></script>
  <script type="text/javascript" src="${r"${ctx }"}/static/component/My97DatePicker/4.8/WdatePicker.js"></script>
  <script type="text/javascript" src="${r"${ctx }"}/static/scripts/utils/jquery.form-1.1.js"></script>
  <script type="text/javascript" src="${r"${ctx }"}/static/scripts/utils/jquery.json-2.2.min.js"></script>
  <script type="text/javascript" src="${r"${ctx }"}/static/scripts/utils/validator1.05.js"></script>
  <script type="text/javascript" src="${r"${ctx }"}/static/scripts/apps/common.js"></script>
  <script type="text/javascript">
    layui.use(['jquery','layer', 'form'], function(){
      var layer = layui.layer
      ,form = layui.form
      ,$ = layui.jquery;
      
    });
    
  	(function($) {
  		$(document).ready(function(){
  			$.ecApp.resizeGridHeight({h:220,limit:false,idArr:["searchForm"]});
 			
 			var initFormData = $('#searchForm').getForm();
  			$('#tableList').datagrid({queryParams:initFormData});
 			
  			$('#searchBtn').click(function(){
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
  	
  	var _frameTabId=${r"'${param._frameTabId}'"};
  	var _appBaseUrl=context+'console/${table_name?uncap_first}';
  	var umsHasEditPMS=${r"${_umsHasEditPMS}"};
 	var rctoolbar=[];
  			  	
	function formatOpType(value,rec){
		var tmp='<a href="javascript:void(0);" onclick="showAppInfo(\''+rec.${pkColumn.changeColumnName?uncap_first}+'\');">'+'修改'+'</a>';
	    return tmp;	
	}
	
	//var _appFieldOptionArr[][]=[][];
    <#if model_column?exists>
        <#list model_column as model>
            <#if (model.options ?exists) >	
	function format${model.changeColumnName}(value,rec){
        if(''==value){
            return '未知';
        }
        <#list model.options as oneOption>
        else if('${oneOption.value}'==value){
            return '${oneOption.name}';
        }
        </#list>
        return ''; 
    }
            </#if>
        </#list>
    </#if>
		
 	function showAppInfo(id){
   		var url=_appBaseUrl+'/update/'+id+'?_parentFrameTabId='+_frameTabId;
 		parent.addFrameTab({title:'${table_annotation}修改',href:url,reload:true,frameId:'vc-param-resultcode-modify-FrameID'});
 	}
 	
 	if(umsHasEditPMS){
 	
		rctoolbar = [{
			text:'新增',
			iconCls:'icon-add',
			handler:function(){
	   			var url=_appBaseUrl+'/create?_parentFrameTabId='+_frameTabId;
	   			if(parent.addFrameTab){
	   				parent.addFrameTab({title:'${table_annotation}新增',href:url,reload:true,frameId:'vc-param-resultcode-add-FrameID'});
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
			       $.messager.confirm('提示', '您确认要删除选中的记录?', function(r){
		                if (r){					
							var selRowsIdArr=[];
							$.each(selRows,function(index){
								selRowsIdArr[index]=this.${pkColumn.changeColumnName?uncap_first};
							});
							var sfJson={};
							var rnd=parseInt(Math.random()*10000);
							sfJson.rnd=rnd;
							sfJson.selIds=$.toJSON(selRowsIdArr);
							var url=_appBaseUrl+'/delete';
			  				$.post(url,sfJson,function(resp){
			  				    $.messager.alert('操作提示',resp.msg,'info');
			  					if(resp.code==0){
			  						 $('#reloadBtnID').click();
			  					}
			  				},"json");
		                }
		            });		
				}  			
			}
		}];
 	}	
  </script>      
  </body>
</html>
