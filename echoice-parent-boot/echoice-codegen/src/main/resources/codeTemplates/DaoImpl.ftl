package ${package_name}.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.echoice.modules.persistence.BaseCommonDao;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import ${package_name}.domain.${table_name};

public class ${table_name}DaoImpl extends BaseCommonDao{
	
	public Page<${table_name}> findPageList(${table_name} searchForm, int pageNo, int pageSize) {
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		<#if model_column?exists>
            <#list model_column as model>
                <#if model_index=0>
        sb.append("t.${model.columnName}");
                <#else>
        sb.append(",t.${model.columnName}");
                </#if>
            </#list>
        </#if>
		
		sb.append(" from ${table_name_small} t where 1=1");
		
		<#if model_column?exists>
        	<#list model_column as model>
        	   <#if (!(model.changeColumnName = 'CreateTime' || model.changeColumnName = 'OpTime')) > 
			       <#if (model.javaType = 'String') >
		if(StringUtils.isNotBlank(searchForm.get${model.changeColumnName}())){
		              <#if (model.options ?exists || model.changeColumnName = 'CreateUser' || model.changeColumnName = 'OpUser') >
			sb.append(" and t.${model.columnName} = ?");
			params.add(searchForm.get${model.changeColumnName}());
			          <#else>
            sb.append(" and t.${model.columnName} like ?");
            params.add("%"+searchForm.get${model.changeColumnName}()+"%");			          
			          </#if>
		}
				    <#else>
		if(searchForm.get${model.changeColumnName}()!=null){
			sb.append(" and t.${model.columnName} = ?");
			params.add(searchForm.get${model.changeColumnName}());
		}
        	        </#if>
        	  </#if>
        	  
        	</#list>
    	</#if>
        if(StringUtils.isNotBlank(searchForm.getAppFormStartTime())){
            sb.append(" and t.op_time >= ?");
            params.add(searchForm.getAppFormStartTime());
        }
        
        if(StringUtils.isNotBlank(searchForm.getAppFormEndTime())){
            sb.append(" and t.op_time <= ?");
            params.add(searchForm.getAppFormEndTime());
        }    	
		sb.append(" order by t.${pkColumn.columnName} desc ");
		return super.findPageSQLData(sb.toString(), new BeanPropertyRowMapper<${table_name}>(${table_name}.class),pageNo, pageSize, params.toArray());
	}
}
