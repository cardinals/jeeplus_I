/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tb_equipment.entity;

import com.jeeplus.modules.sys.entity.Area;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 违法人员数据Entity
 * @author zgb
 * @version 2018-09-30
 */
public class IllegalData extends DataEntity<IllegalData> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名字
	private String idCard;		// 身份证号
	private Area area;		// 人员区域
	private String photoes;		// 违法的图片，以逗号分隔
	
	public IllegalData() {
		super();
	}

	public IllegalData(String id){
		super(id);
	}

	@ExcelField(title="名字", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="身份证号", align=2, sort=8)
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	@ExcelField(title="人员区域", fieldType=Area.class, value="area.name", align=2, sort=9)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@ExcelField(title="违法的图片，以逗号分隔", align=2, sort=10)
	public String getPhotoes() {
		return photoes;
	}

	public void setPhotoes(String photoes) {
		this.photoes = photoes;
	}
	
}