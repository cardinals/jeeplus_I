/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tb_account.dao;

import com.jeeplus.common.persistence.TreeDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.tb_account.entity.Account;

/**
 * 账户表DAO接口
 * @author zgb
 * @version 2018-09-30
 */
@MyBatisDao
public interface AccountDao extends TreeDao<Account> {
	
}