/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tb_facility.web;

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
import com.jeeplus.modules.tb_facility.entity.Facility;
import com.jeeplus.modules.tb_facility.service.FacilityService;

/**
 * 设备表Controller
 * @author zgb
 * @version 2018-10-11
 */
@Controller
@RequestMapping(value = "${adminPath}/tb_facility/facility")
public class FacilityController extends BaseController {

	@Autowired
	private FacilityService facilityService;
	
	@ModelAttribute
	public Facility get(@RequestParam(required=false) String id) {
		Facility entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = facilityService.get(id);
		}
		if (entity == null){
			entity = new Facility();
		}
		return entity;
	}
	
	/**
	 * 设备表功能列表页面
	 */
	@RequiresPermissions("tb_facility:facility:list")
	@RequestMapping(value = {"list", ""})
	public String list(Facility facility, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Facility> page = facilityService.findPage(new Page<Facility>(request, response), facility); 
		model.addAttribute("page", page);
		return "modules/tb_facility/facilityList";
	}

	/**
	 * 查看，增加，编辑设备表功能表单页面
	 */
	@RequiresPermissions(value={"tb_facility:facility:view","tb_facility:facility:add","tb_facility:facility:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Facility facility, Model model) {
		model.addAttribute("facility", facility);
		return "modules/tb_facility/facilityForm";
	}

	/**
	 * 保存设备表功能
	 */
	@RequiresPermissions(value={"tb_facility:facility:add","tb_facility:facility:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Facility facility, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, facility)){
			return form(facility, model);
		}
		if(!facility.getIsNewRecord()){//编辑表单保存
			Facility t = facilityService.get(facility.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(facility, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			facilityService.save(t);//保存
		}else{//新增表单保存
			facilityService.save(facility);//保存
		}
		addMessage(redirectAttributes, "保存设备表功能成功");
		return "redirect:"+Global.getAdminPath()+"/tb_facility/facility/?repage";
	}
	
	/**
	 * 删除设备表功能
	 */
	@RequiresPermissions("tb_facility:facility:del")
	@RequestMapping(value = "delete")
	public String delete(Facility facility, RedirectAttributes redirectAttributes) {
		facilityService.delete(facility);
		addMessage(redirectAttributes, "删除设备表功能成功");
		return "redirect:"+Global.getAdminPath()+"/tb_facility/facility/?repage";
	}
	
	/**
	 * 批量删除设备表功能
	 */
	@RequiresPermissions("tb_facility:facility:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			facilityService.delete(facilityService.get(id));
		}
		addMessage(redirectAttributes, "删除设备表功能成功");
		return "redirect:"+Global.getAdminPath()+"/tb_facility/facility/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("tb_facility:facility:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Facility facility, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "设备表功能"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Facility> page = facilityService.findPage(new Page<Facility>(request, response, -1), facility);
    		new ExportExcel("设备表功能", Facility.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出设备表功能记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/tb_facility/facility/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("tb_facility:facility:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Facility> list = ei.getDataList(Facility.class);
			for (Facility facility : list){
				try{
					facilityService.save(facility);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设备表功能记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条设备表功能记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入设备表功能失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/tb_facility/facility/?repage";
    }
	
	/**
	 * 下载导入设备表功能数据模板
	 */
	@RequiresPermissions("tb_facility:facility:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "设备表功能数据导入模板.xlsx";
    		List<Facility> list = Lists.newArrayList(); 
    		new ExportExcel("设备表功能数据", Facility.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/tb_facility/facility/?repage";
    }
	
	
	

}