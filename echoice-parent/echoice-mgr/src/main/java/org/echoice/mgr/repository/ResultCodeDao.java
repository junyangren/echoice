package org.echoice.mgr.repository;

import org.echoice.mgr.entity.ResultCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResultCodeDao extends JpaRepository<ResultCode,String>,JpaSpecificationExecutor<ResultCode>{

}
