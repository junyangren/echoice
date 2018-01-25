<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/commons/meta-app.jsp" %>
	<title>${table_annotation}</title>
	<%@ include file="/commons/layui-css.jsp" %>
	<link rel="stylesheet" type="text/css" href="${r"${ctx }"}/static/styles/app.css" />
  </head>
  <body>
  	<div class="header-navbar ec-fix-top">当前位置：首页->${table_annotation}</div>
  	<div class="ec-form ec-auto ec-bdc ec-p10 ec-form-mtb">
		<blockquote class="ec-elem-quote">${table_annotation}</blockquote>
		<form class="layui-form" action="" method="post" name="ec-edit-form" id="ec-edit-form">
		<#if model_column?exists>
        	<#list model_column as model>
        	<#if (!(model.changeColumnName = 'CreateTime' ||model.changeColumnName = 'CreateUser' || model.changeColumnName = 'OpTime' || model.changeColumnName = 'OpUser')) >		  
		  <div class="layui-form-item">
		    <label class="layui-form-label">${model.columnShortComment!}：</label>
		    <#if (model.pk) >
		    <div class="layui-input-inline">
		      <input type="text" name="${model.changeColumnName?uncap_first}" value="${r"$"}{listone.${model.changeColumnName?uncap_first}}" lay-verify="" class="layui-input layui-disabled" readonly />	
		    </div>
		    <#elseif (model.options ?exists) >
		    <div class="layui-input-inline">
                <select name="${model.changeColumnName?uncap_first}" lay-verify="required">
                  <option value="">请选择</option>
                  <#list model.options as oneOption>
                  <option value="${oneOption.value}"<c:if test="${r"$"}{listone.${model.changeColumnName?uncap_first}=='${oneOption.value}'}"> selected</c:if>>${oneOption.name}</option>
                  </#list>
                </select>
		    </div>		    	    
		    <#elseif (model.javaType = 'Date') >
		    <div class="layui-input-inline">
		      <input type="text" name="${model.changeColumnName?uncap_first}" value="<fmt:formatDate value="${r"$"}{listone.${model.changeColumnName?uncap_first}}" pattern="yyyy-MM-dd" />" lay-verify="<#if (model.required) >required</#if>" placeholder="请输入${model.columnShortComment!}" autocomplete="off" class="layui-input" readonly onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />	
		    </div>
		    <#elseif (model.javaType = 'Integer'||model.javaType = 'Long'||model.javaType = 'Double') >
		    <div class="layui-input-inline">
		      <input type="text" name="${model.changeColumnName?uncap_first}" value="${r"$"}{listone.${model.changeColumnName?uncap_first}}" lay-verify="<#if (model.required) >required|number</#if>" placeholder="请输入${model.columnShortComment!}" maxlength="${model.columnSize}" autocomplete="off" class="layui-input" />	
		    </div>		    
			<#else>
				<#if (model.columnSize<=64) >
		    <div class="layui-input-block">
		      <input type="text" name="${model.changeColumnName?uncap_first}" value="${r"$"}{listone.${model.changeColumnName?uncap_first}}" lay-verify="<#if (model.required) >required</#if>" placeholder="请输入${model.columnShortComment!}" maxlength="${model.columnSize}" autocomplete="off" class="layui-input" />	
		    </div>
		    	<#else>
		    <div class="layui-input-block">
		      <textarea name="${model.changeColumnName?uncap_first}" lay-verify="<#if (model.required) >required</#if>" placeholder="请输入${model.columnShortComment!}" maxlength="${model.columnSize}" class="layui-textarea">${r"$"}{listone.${model.changeColumnName?uncap_first}}</textarea>	
		    </div>		    	
		        </#if>	    
		    </#if>
		  </div>
		  	</#if>  
        	</#list>
    	</#if>
    	  <c:if test="${r"${_umsHasEditPMS}"}">
		  <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button type="button" class="layui-btn" lay-submit lay-filter="ec-from">提交</button>
		      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
		    </div>
		  </div>
		  </c:if>		  		  		  
		</form>  	
  	</div>
  	<script type="text/javascript" src="${r"${ctx}"}/static/component/My97DatePicker/4.8/WdatePicker.js"></script>
	<%@ include file="/commons/layui-js.jsp" %>
	<script>
	 
	//一般直接写在一个js文件中
	layui.use(['jquery','layer', 'form'], function(){
	  var layer = layui.layer
	  ,form = layui.form
	  ,$ = layui.jquery;
	  
	  var vframeId = '${r"${param._parentFrameTabId }'"};
      var jsContext='${r"${ctx}"}';  
	  //监听提交
	  form.on('submit(ec-from)', function(data){
	    var sfJson=data.field;
	    var rnd=parseInt(Math.random()*1000000);
		sfJson.rnd=rnd;		
        $.post(jsContext+"/console/${table_name?uncap_first}/update",sfJson,function(resp){
            if(resp){
                $('input[name="${pkColumn.changeColumnName?uncap_first}"]').val(resp.data);
                if(resp.code==0){
                    layer.confirm(resp.msg, {
                          btn: ['继续编辑','关闭'] //按钮
                        },function(index){
                          layer.close(index);
                        }, function(index){
                          //window.location.href=jsContext+'/console/${table_name?uncap_first}/create?_parentFrameTabId='+vframeId;
                          parent.closeFrameTab();
                        });
                    
                    if(vframeId!=''){
                        parent.reloadFrame({frameId:vframeId});
                    }                
                }else{
                    layer.alert(resp.msg);
                }
            }else{
                layer.alert('操作失败');
            }
            
        },"json");
        		
	    return false;
	  });
	});
	</script> 	
  </body>
</html>
