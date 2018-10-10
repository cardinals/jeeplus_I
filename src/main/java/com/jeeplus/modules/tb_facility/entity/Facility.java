/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tb_facility.entity;

import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 设备表Entity
 * @author zgb
 * @version 2018-10-10
 */
public class Facility extends DataEntity<Facility> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 设备编号
	private String name;		// 设备名
	private Area area;		// 设备区域
	private User user;		// 设备管理员
	private String type;		// 设备类型
	
	public Facility() {
		super();
	}

	public Facility(String id){
		super(id);
	}

	@ExcelField(title="设备编号", align=2, sort=7)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@ExcelField(title="设备名", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="设备区域", fieldType=Area.class, value="area.name", align=2, sort=9)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@ExcelField(title="设备管理员", fieldType=User.class, value="user.name", align=2, sort=10)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ExcelField(title="设备类型", dictType="", align=2, sort=11)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}