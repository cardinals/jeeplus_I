/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tb_account.entity;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.jeeplus.common.persistence.TreeEntity;

/**
 * 账户表Entity
 * @author zgb
 * @version 2018-09-30
 */
public class Account extends TreeEntity<Account> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 账户名称
	private Integer sort;		// 排序
	private Account parent;		// 父及编号
	private String parentIds;		// 所有父及编号
	
	public Account() {
		super();
	}

	public Account(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="排序不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@JsonBackReference
	@NotNull(message="父及编号不能为空")
	public Account getParent() {
		return parent;
	}

	public void setParent(Account parent) {
		this.parent = parent;
	}
	
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}