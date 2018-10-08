/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tb_account.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.tb_account.entity.Account;
import com.jeeplus.modules.tb_account.service.AccountService;

/**
 * 账户表Controller
 * @author zgb
 * @version 2018-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/tb_account/account")
public class AccountController extends BaseController {

	@Autowired
	private AccountService accountService;
	
	@ModelAttribute
	public Account get(@RequestParam(required=false) String id) {
		Account entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountService.get(id);
		}
		if (entity == null){
			entity = new Account();
		}
		return entity;
	}
	
	/**
	 * 账户列表页面
	 */
	@RequiresPermissions("tb_account:account:list")
	@RequestMapping(value = {"list", ""})
	public String list(Account account, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Account> list = accountService.findList(account); 
		model.addAttribute("list", list);
		return "modules/tb_account/accountList";
	}

	/**
	 * 查看，增加，编辑账户表单页面
	 */
	@RequiresPermissions(value={"tb_account:account:view","tb_account:account:add","tb_account:account:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Account account, Model model) {
		if (account.getParent()!=null && StringUtils.isNotBlank(account.getParent().getId())){
			account.setParent(accountService.get(account.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(account.getId())){
				Account accountChild = new Account();
				accountChild.setParent(new Account(account.getParent().getId()));
				List<Account> list = accountService.findList(account); 
				if (list.size() > 0){
					account.setSort(list.get(list.size()-1).getSort());
					if (account.getSort() != null){
						account.setSort(account.getSort() + 30);
					}
				}
			}
		}
		if (account.getSort() == null){
			account.setSort(30);
		}
		model.addAttribute("account", account);
		return "modules/tb_account/accountForm";
	}

	/**
	 * 保存账户
	 */
	@RequiresPermissions(value={"tb_account:account:add","tb_account:account:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Account account, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, account)){
			return form(account, model);
		}
		if(!account.getIsNewRecord()){//编辑表单保存
			Account t = accountService.get(account.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(account, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			accountService.save(t);//保存
		}else{//新增表单保存
			accountService.save(account);//保存
		}
		addMessage(redirectAttributes, "保存账户成功");
		return "redirect:"+Global.getAdminPath()+"/tb_account/account/?repage";
	}
	
	/**
	 * 删除账户
	 */
	@RequiresPermissions("tb_account:account:del")
	@RequestMapping(value = "delete")
	public String delete(Account account, RedirectAttributes redirectAttributes) {
		accountService.delete(account);
		addMessage(redirectAttributes, "删除账户成功");
		return "redirect:"+Global.getAdminPath()+"/tb_account/account/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Account> list = accountService.findList(new Account());
		for (int i=0; i<list.size(); i++){
			Account e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}