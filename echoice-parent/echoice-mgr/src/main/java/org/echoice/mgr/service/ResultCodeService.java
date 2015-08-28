package org.echoice.mgr.service;

import java.util.List;
import java.util.Map;

import org.echoice.mgr.entity.ResultCode;
import org.echoice.mgr.repository.ResultCodeDao;
import org.echoice.modules.persistence.DynamicSpecifications;
import org.echoice.modules.persistence.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResultCodeService {
	@Autowired
	private ResultCodeDao resultCodeDao;
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Page<ResultCode> findPageList(Map<String, Object> searchParams,int pageNumber, int pageSize){
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "auto");
		Specification<ResultCode> spec = buildSpecification(searchParams);
		return resultCodeDao.findAll(spec, pageRequest);
	}
	/**
	 * 批理删除错误码配置
	 * @param codeList
	 */
	public void batchDelete(List<String> codeList ){
		for (String code : codeList) {
			resultCodeDao.delete(code);
		}
	}
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.ASC, "code");
		} else if ("errmessages".equals(sortType)) {
			sort = new Sort(Direction.ASC, "errmessages");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<ResultCode> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<ResultCode> spec = DynamicSpecifications.bySearchFilter(filters.values(), ResultCode.class);
		return spec;
	}
	
	/**
	 * 默认查询/get开启只读事务
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public ResultCodeDao getResultCodeDao() {
		return resultCodeDao;
	}

	public void setResultCodeDao(ResultCodeDao resultCodeDao) {
		this.resultCodeDao = resultCodeDao;
	}
	
}
