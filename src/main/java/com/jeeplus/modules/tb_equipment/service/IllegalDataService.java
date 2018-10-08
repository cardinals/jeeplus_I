/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tb_equipment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.tb_equipment.entity.IllegalData;
import com.jeeplus.modules.tb_equipment.dao.IllegalDataDao;

/**
 * 违法人员数据Service
 * @author zgb
 * @version 2018-09-30
 */
@Service
@Transactional(readOnly = true)
public class IllegalDataService extends CrudService<IllegalDataDao, IllegalData> {

	public IllegalData get(String id) {
		return super.get(id);
	}
	
	public List<IllegalData> findList(IllegalData illegalData) {
		return super.findList(illegalData);
	}
	
	public Page<IllegalData> findPage(Page<IllegalData> page, IllegalData illegalData) {
		return super.findPage(page, illegalData);
	}
	
	@Transactional(readOnly = false)
	public void save(IllegalData illegalData) {
		super.save(illegalData);
	}
	
	@Transactional(readOnly = false)
	public void delete(IllegalData illegalData) {
		super.delete(illegalData);
	}
	
	
	
	
}