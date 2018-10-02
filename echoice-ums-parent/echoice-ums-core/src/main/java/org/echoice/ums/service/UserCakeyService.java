package org.echoice.ums.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import org.echoice.ums.dao.UserCakeyDao;
import org.echoice.ums.domain.UserCakey;
import org.echoice.ums.web.view.MsgTipExt;

public interface UserCakeyService {

	public Page<UserCakey> findPageList(Map<String, Object> searchParams,int pageNumber, int pageSize);
	
	public void batchDelete(List<Long> idList);
	
	public UserCakeyDao getUserCakeyDao();
	
	/**
	 * 单个key领取、标记挂失、离职归还
	 * @param userCakey
	 * @return
	 */
	public MsgTipExt saveForOptKey(UserCakey userCakey);
	
	/**
	 * 按部门领取
	 * @param userCakey
	 * @return
	 */
	public MsgTipExt saveIssueByDept(UserCakey userCakey);
}