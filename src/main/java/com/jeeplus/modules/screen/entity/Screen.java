/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.screen.entity;

import com.jeeplus.modules.sys.entity.Area;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 屏幕控制Entity
 * @author zgb
 * @version 2018-09-30
 */
public class Screen extends DataEntity<Screen> {
	
	private static final long serialVersionUID = 1L;
	private Area area;		// 区域
	private String type;		// 默认类型
	private String photoAndVideo;		// 图片或视频
	private String article;		// 文字
	private String backgroundPhoto;		// 背景图
	private String logo;		// 图标
	
	public Screen() {
		super();
	}

	public Screen(String id){
		super(id);
	}

	@ExcelField(title="区域", fieldType=Area.class, value="area.name", align=2, sort=7)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@ExcelField(title="默认类型", dictType="", align=2, sort=8)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="图片或视频", align=2, sort=9)
	public String getPhotoAndVideo() {
		return photoAndVideo;
	}

	public void setPhotoAndVideo(String photoAndVideo) {
		this.photoAndVideo = photoAndVideo;
	}
	
	@ExcelField(title="文字", align=2, sort=10)
	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}
	
	@ExcelField(title="背景图", align=2, sort=11)
	public String getBackgroundPhoto() {
		return backgroundPhoto;
	}

	public void setBackgroundPhoto(String backgroundPhoto) {
		this.backgroundPhoto = backgroundPhoto;
	}
	
	@ExcelField(title="图标", align=2, sort=12)
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}