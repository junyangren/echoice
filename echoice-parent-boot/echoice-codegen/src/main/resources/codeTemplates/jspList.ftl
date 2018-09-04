<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/commons/meta-app.jsp" %>
  <%@ include file="/commons/easyui-css.jsp" %>
  <%@ include file="/commons/layui-css.jsp" %>
  <link rel="stylesheet" type="text/css" href="${r"${ctx }"}/static/styles/app.css" /> 
  </head>
  <body>
  	<div class="header-navbar">当前位置：首页->${table_annotation}</div>
  	<div class="ec-mlr-6">
	  	<div style="margin-top: 12px;">
	  		<form id="searchForm" name="searchForm" action="" method="post" target="_blank" class="layui-form ec-search-form">
               <div class="layui-form-item">
                <#if model_column?exists>
                    <#list model_column as model>
                    <#if (model.required && !(model.changeColumnName = 'CreateTime' ||model.changeColumnName = 'CreateUser' || model.changeColumnName = 'OpTime' || model.changeColumnName = 'OpUser')) >              
                   <div class="layui-inline">
                       <label class="layui-form-label">${model.columnShortComment!}：</label>
                       <div class="layui-input-inline">
                       <#if (model.javaType = 'Date') >
                            <input type="text" name="${model.changeColumnName?uncap_first}" placeholder="请输入${model.columnShortComment!}" autocomplete="off" class="layui-input" readonly onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                       <#elseif (model.options ?exists) >
                            <select name="${model.changeColumnName?uncap_first}">
                              <option value="">请选择</option>
                              <#list model.options as oneOption>
                              <option value="${oneOption.value}">${oneOption.name}</option>
                              </#list>
                            </select>
                       <#elseif (model.javaType = 'Integer'||model.javaType = 'Long'||model.javaType = 'Double') >
                            <input type="number" name="${model.changeColumnName?uncap_first}" placeholder="请输入${model.columnShortComment!}" autocomplete="off" class="layui-input" />                                                        
                       <#else>
                            <input type="text" name="${model.changeColumnName?uncap_first}" placeholder="请输入${model.columnShortComment!}" autocomplete="off" class="layui-input" />
                       </#if>
                       </div>
                   </div>
                   </#if>
                   </#list>
                </#if>
                <div class="layui-inline">
                   <label class="layui-form-label">开始时间：</label>
                   <div class="layui-input-inline">
                        <input class="layui-input" id="appFormStartTime" name="appFormStartTime" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'appFormEndTime\')}'})" />
                    </div>
                </div>
                <div class="layui-inline">
                   <label class="layui-form-label">结束时间：</label>
                   <div class="layui-input-inline">
                        <input class="layui-input" id="appFormEndTime" name="appFormEndTime" type="text"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'appFormStartTime\')}'})" />
                   </div>
                </div>                    
                 <div class="layui-inline">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline">
                        <button id="searchBtn" type="button" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:60px">查询</button>
                        <button id="resetBtn" type="button" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:60px">重置</button>
                        <button id="reloadBtnID" type="button" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="width:60px;">刷新</button>
                    </div>
                 </div>
               </div>                       	  			
	  		</form>
	  	</div>
	  	<div id="tableListDiv" style="width: 100%;height: 380px;padding-top: 0px">
			<table id="tableList" title="查询列表"
					data-options="
						fit:true,
						fitColumns:false,
						idField:'${pkColumn.changeColumnName?uncap_first}',
						singleSelect: false,
						selectOnCheck:true,
						collapsible: false,
						rownumbers: false,
						url: '${r"${ctx }"}/console/${table_name?uncap_first}/searchJSON',
						method: 'post',
						pagination:true,
						pageSize:20,
						toolbar:$.ecApp.rctoolbar,
						onBeforeLoad:function(parma){
							$('#tableList').datagrid('unselectAll');
						}
					">
				<thead data-options="frozen:true">
					<tr>
						<th data-options="field:'${pkColumn.changeColumnName?uncap_first}Check',checkbox:true">选择</th>
						<th data-options="field:'opType',width:60,align:'center',formatter:$.ecApp.formatOpType">操作</th>
						<th data-options="field:'${pkColumn.changeColumnName?uncap_first}',width:80,align:'center'">${pkColumn.columnShortComment!}</th>
					</tr>
				</thead>			
				<thead>
					<tr>
					    <#if model_column?exists>
        					<#list model_column as model>
        						<#if (!model.pk) >
						<th data-options="field:'${model.changeColumnName?uncap_first}',width:120,align:'center'<#if (model.options ?exists) >,formatter:$.ecApp.format${model.changeColumnName}</#if>">${model.columnShortComment!}</th>
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
  <%@ include file="/commons/layui-js.jsp" %>
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
               
        $.ecApp=$.ecApp||{};
        $.ecApp.frameTabId=${r"'${param._frameTabId}'"};
        $.ecApp.appBaseUrl=context+'console/${table_name?uncap_first}';
        $.ecApp.umsHasEditPMS=${r"${_umsHasEditPMS}"};
        $.ecApp.rctoolbar=[];
        $.ecApp.formatOpType=function(value,rec){
            var tmp='<a href="javascript:void(0);" onclick="$.ecApp.showAppInfo(\''+rec.${pkColumn.changeColumnName?uncap_first}+'\');">'+'修改'+'</a>';
            return tmp;
        }

        $.ecApp.showAppInfo=function(id){
            var url=$.ecApp.appBaseUrl+'/update/'+id+'?_parentFrameTabId='+$.ecApp.frameTabId;
            parent.addFrameTab({title:'修改${table_annotation}',href:url,reload:true,frameId:'${table_name?uncap_first}-modify-FrameID'});
        }
        
        $.ecApp.initRctoolbar=function(umsHasEditPMS){
            if(umsHasEditPMS){
                $.ecApp.rctoolbar = [{
                    text:'新增',
                    iconCls:'icon-add',
                    handler:function(){
                        var url=$.ecApp.appBaseUrl+'/create?_parentFrameTabId='+$.ecApp.frameTabId;
                        if(parent.addFrameTab){
                            parent.addFrameTab({title:'新增${table_annotation}',href:url,reload:true,frameId:'${table_name?uncap_first}-add-FrameID'});
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
                                    var url=$.ecApp.appBaseUrl+'/delete';
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
        }
        
        <#if model_column?exists>
            <#list model_column as model>
                <#if (model.options ?exists) >  
        $.ecApp.format${model.changeColumnName}=function(value,rec){
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
        
       $(document).ready(function(){
            
            $.ecApp.initRctoolbar($.ecApp.umsHasEditPMS);
            
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
        
    });
  </script>      
  </body>
</html>
