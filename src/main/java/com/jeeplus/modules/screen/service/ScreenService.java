/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.screen.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.screen.entity.Screen;
import com.jeeplus.modules.screen.dao.ScreenDao;

/**
 * 屏幕控制Service
 * @author zgb
 * @version 2018-09-30
 */
@Service
@Transactional(readOnly = true)
public class ScreenService extends CrudService<ScreenDao, Screen> {

	public Screen get(String id) {
		return super.get(id);
	}
	
	public List<Screen> findList(Screen screen) {
		return super.findList(screen);
	}
	
	public Page<Screen> findPage(Page<Screen> page, Screen screen) {
		return super.findPage(page, screen);
	}
	
	@Transactional(readOnly = false)
	public void save(Screen screen) {
		super.save(screen);
	}
	
	@Transactional(readOnly = false)
	public void delete(Screen screen) {
		super.delete(screen);
	}
	
	
	
	
}