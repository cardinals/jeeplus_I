/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.screen.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.screen.entity.Screen;

/**
 * 屏幕控制DAO接口
 * @author zgb
 * @version 2018-09-30
 */
@MyBatisDao
public interface ScreenDao extends CrudDao<Screen> {

	
}