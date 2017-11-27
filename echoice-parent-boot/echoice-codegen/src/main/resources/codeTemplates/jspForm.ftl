<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html>
  <head>
	<meta charset="utf-8">
	<title>${table_annotation}</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black"> 
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%@ include file="/commons/layui-css.jsp" %>
	<link rel="stylesheet" type="text/css" href="${r"${ctx }"}/static/styles/quickLayout/quick-layout-min.css" />
	<link rel="stylesheet" type="text/css" href="${r"${ctx }"}/static/styles/app.css" />
  </head>
  <body>
  	<div class="header-navbar ec-fix-top">当前位置：首页->${table_annotation}</div>
  	<div class="ec-form auto bdc p10 ec-form-mtb">
		<blockquote class="ec-elem-quote">${table_annotation}</blockquote>
		<form class="layui-form" action="">
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
		  <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit lay-filter="ec-from">提交</button>
		      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
		    </div>
		  </div>		  		  		  
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

	  //监听提交
	  form.on('submit(ec-from)', function(data){
	    var sfJson=data.field;
	    var rnd=parseInt(Math.random()*1000000);
		sfJson.rnd=rnd;
		$.post("${r"${ctx}"}/console/${table_name?uncap_first}/update",sfJson,function(data){
			layer.alert(data.msg);
			if(data.code==0&&vframeId!=''){
				parent.reloadFrame({frameId:vframeId});
			}
		},"json");
	    return false;
	  });
	});
	</script> 	
  </body>
</html>
