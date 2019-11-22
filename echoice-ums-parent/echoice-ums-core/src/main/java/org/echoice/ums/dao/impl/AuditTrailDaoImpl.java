package org.echoice.ums.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.echoice.modules.persistence.BaseCommonDao;
import org.echoice.modules.web.paper.PageBean;
import org.echoice.ums.domain.AuditTrailEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

public class AuditTrailDaoImpl extends BaseCommonDao{
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public PageBean findPageCondition(AuditTrailEntity searchForm, int pageNo,int pageSize) {
		// TODO Auto-generated method stub
		String hql="select t from AuditTrailEntity t where 1=1";
		List<Object> paramValues=Lists.newArrayList();
		int sqlPost=0;
		if(StringUtils.isNotBlank(searchForm.getClientIp())){
			hql+=" and t.clientIp like ?"+(++sqlPost);
			paramValues.add("%"+searchForm.getClientIp().trim()+"%");
		}
		
		if(StringUtils.isNotBlank(searchForm.getApplicationCode())){
			hql+=" and t.applicationCode like ?"+(++sqlPost);
			paramValues.add("%"+searchForm.getApplicationCode().trim()+"%");
		}
		
		if(searchForm.getRecordDate()!=null){
			hql+=" and t.recordDate >= ?"+(++sqlPost);
			paramValues.add(searchForm.getRecordDate());
		}
		
		hql+=" order by t.recordDate desc";
		return super.findPageHQL(hql, pageNo, pageSize, paramValues.toArray());
	}
}
