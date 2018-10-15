/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tb_facility.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.tb_facility.entity.Facility;

/**
 * 设备表DAO接口
 * @author zgb
 * @version 2018-10-11
 */
@MyBatisDao
public interface FacilityDao extends CrudDao<Facility> {

	
}