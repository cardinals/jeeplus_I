/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tb_account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.tb_account.entity.Account;
import com.jeeplus.modules.tb_account.dao.AccountDao;

/**
 * 账户表Service
 * @author zgb
 * @version 2018-09-30
 */
@Service
@Transactional(readOnly = true)
public class AccountService extends TreeService<AccountDao, Account> {

	public Account get(String id) {
		return super.get(id);
	}
	
	public List<Account> findList(Account account) {
		if (StringUtils.isNotBlank(account.getParentIds())){
			account.setParentIds(","+account.getParentIds()+",");
		}
		return super.findList(account);
	}
	
	@Transactional(readOnly = false)
	public void save(Account account) {
		super.save(account);
	}
	
	@Transactional(readOnly = false)
	public void delete(Account account) {
		super.delete(account);
	}
	
}