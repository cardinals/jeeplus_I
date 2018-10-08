/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tb_equipment.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.tb_equipment.entity.IllegalData;
import com.jeeplus.modules.tb_equipment.service.IllegalDataService;

/**
 * 违法人员数据Controller
 * @author zgb
 * @version 2018-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/tb_equipment/illegalData")
public class IllegalDataController extends BaseController {

	@Autowired
	private IllegalDataService illegalDataService;
	
	@ModelAttribute
	public IllegalData get(@RequestParam(required=false) String id) {
		IllegalData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = illegalDataService.get(id);
		}
		if (entity == null){
			entity = new IllegalData();
		}
		return entity;
	}
	
	/**
	 * 违法人员数据列表页面
	 */
	@RequiresPermissions("tb_equipment:illegalData:list")
	@RequestMapping(value = {"list", ""})
	public String list(IllegalData illegalData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IllegalData> page = illegalDataService.findPage(new Page<IllegalData>(request, response), illegalData); 
		model.addAttribute("page", page);
		return "modules/tb_equipment/illegalDataList";
	}

	/**
	 * 查看，增加，编辑违法人员数据表单页面
	 */
	@RequiresPermissions(value={"tb_equipment:illegalData:view","tb_equipment:illegalData:add","tb_equipment:illegalData:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(IllegalData illegalData, Model model) {
		model.addAttribute("illegalData", illegalData);
		return "modules/tb_equipment/illegalDataForm";
	}

	/**
	 * 保存违法人员数据
	 */
	@RequiresPermissions(value={"tb_equipment:illegalData:add","tb_equipment:illegalData:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(IllegalData illegalData, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, illegalData)){
			return form(illegalData, model);
		}
		if(!illegalData.getIsNewRecord()){//编辑表单保存
			IllegalData t = illegalDataService.get(illegalData.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(illegalData, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			illegalDataService.save(t);//保存
		}else{//新增表单保存
			illegalDataService.save(illegalData);//保存
		}
		addMessage(redirectAttributes, "保存违法人员数据成功");
		return "redirect:"+Global.getAdminPath()+"/tb_equipment/illegalData/?repage";
	}
	
	/**
	 * 删除违法人员数据
	 */
	@RequiresPermissions("tb_equipment:illegalData:del")
	@RequestMapping(value = "delete")
	public String delete(IllegalData illegalData, RedirectAttributes redirectAttributes) {
		illegalDataService.delete(illegalData);
		addMessage(redirectAttributes, "删除违法人员数据成功");
		return "redirect:"+Global.getAdminPath()+"/tb_equipment/illegalData/?repage";
	}
	
	/**
	 * 批量删除违法人员数据
	 */
	@RequiresPermissions("tb_equipment:illegalData:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			illegalDataService.delete(illegalDataService.get(id));
		}
		addMessage(redirectAttributes, "删除违法人员数据成功");
		return "redirect:"+Global.getAdminPath()+"/tb_equipment/illegalData/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("tb_equipment:illegalData:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(IllegalData illegalData, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "违法人员数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<IllegalData> page = illegalDataService.findPage(new Page<IllegalData>(request, response, -1), illegalData);
    		new ExportExcel("违法人员数据", IllegalData.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出违法人员数据记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/tb_equipment/illegalData/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("tb_equipment:illegalData:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<IllegalData> list = ei.getDataList(IllegalData.class);
			for (IllegalData illegalData : list){
				try{
					illegalDataService.save(illegalData);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条违法人员数据记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条违法人员数据记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入违法人员数据失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/tb_equipment/illegalData/?repage";
    }
	
	/**
	 * 下载导入违法人员数据数据模板
	 */
	@RequiresPermissions("tb_equipment:illegalData:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "违法人员数据数据导入模板.xlsx";
    		List<IllegalData> list = Lists.newArrayList(); 
    		new ExportExcel("违法人员数据数据", IllegalData.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/tb_equipment/illegalData/?repage";
    }
	
	
	

}