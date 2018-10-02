package org.echoice.ums.dao;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.echoice.ums.domain.CakeyOrder;

public interface CakeyOrderDao extends JpaRepository<CakeyOrder,Long>,JpaSpecificationExecutor<CakeyOrder>{
	public Page<CakeyOrder> findPageList(CakeyOrder searchForm, int pageNo, int pageSize);
}