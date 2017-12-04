package ${package_name}.mgr.service.impl;

import java.util.List;
import java.util.Map;

import org.echoice.modules.persistence.DynamicSpecifications;
import org.echoice.modules.persistence.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package_name}.dao.${table_name}Dao;
import ${package_name}.domain.${table_name};
import ${package_name}.mgr.service.${table_name}Service;

/**
* 描述：${table_annotation} 服务实现层
* @author ${author}
* @date ${date}
*/
@Service
public class ${table_name}ServiceImpl implements ${table_name}Service{
	@Autowired
	private ${table_name}Dao ${table_name?uncap_first}Dao;
	public Page<${table_name}> findPageList(Map<String, Object> searchParams,int pageNumber, int pageSize){
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		Specification<${table_name}> spec = buildSpecification(searchParams);
		return ${table_name?uncap_first}Dao.findAll(spec, pageRequest);
	}
	/**
	 * 批理删除${table_annotation}
	 * @param idList
	 */
	@Transactional
	public void batchDelete(List<${pkColumn.javaType}> idList ){
		for (${pkColumn.javaType} code : idList) {
			${table_name?uncap_first}Dao.delete(code);
		}
	}
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "${pkColumn.changeColumnName?uncap_first}");
		}
		
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<${table_name}> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<${table_name}> spec = DynamicSpecifications.bySearchFilter(filters.values(), ${table_name}.class);
		return spec;
	}
	
	/**
	 * 默认查询/get开启只读事务
	 * @return
	 */
	public ${table_name}Dao get${table_name}Dao() {
		return ${table_name?uncap_first}Dao;
	}
}
