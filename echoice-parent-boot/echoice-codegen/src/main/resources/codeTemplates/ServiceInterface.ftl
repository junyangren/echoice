package ${package_name}.mgr.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import ${package_name}.dao.${table_name}Dao;
import ${package_name}.domain.${table_name};

public interface ${table_name}Service {

	public Page<${table_name}> findPageList(Map<String, Object> searchParams,int pageNumber, int pageSize);
	
	public void batchDelete(List<${pkColumn.javaType}> idList);
	
	public ${table_name}Dao get${table_name}Dao();
}