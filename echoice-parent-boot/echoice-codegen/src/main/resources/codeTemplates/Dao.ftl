package ${package_name}.dao;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ${package_name}.domain.${table_name};

public interface ${table_name}Dao extends JpaRepository<${table_name},${pkColumn.javaType}>,JpaSpecificationExecutor<${table_name}>{
	public Page<${table_name}> findPageList(${table_name} searchForm, int pageNo, int pageSize);
}