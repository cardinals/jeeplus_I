/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tb_facility.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.tb_facility.entity.Facility;
import com.jeeplus.modules.tb_facility.dao.FacilityDao;

/**
 * 设备表Service
 * @author zgb
 * @version 2018-10-10
 */
@Service
@Transactional(readOnly = true)
public class FacilityService extends CrudService<FacilityDao, Facility> {

	public Facility get(String id) {
		return super.get(id);
	}
	
	public List<Facility> findList(Facility facility) {
		return super.findList(facility);
	}
	
	public Page<Facility> findPage(Page<Facility> page, Facility facility) {
		return super.findPage(page, facility);
	}
	
	@Transactional(readOnly = false)
	public void save(Facility facility) {
		super.save(facility);
	}
	
	@Transactional(readOnly = false)
	public void delete(Facility facility) {
		super.delete(facility);
	}
	
	
	
	
}