/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.screen.web;

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
import com.jeeplus.modules.screen.entity.Screen;
import com.jeeplus.modules.screen.service.ScreenService;

/**
 * 屏幕控制Controller
 * @author zgb
 * @version 2018-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/screen/screen")
public class ScreenController extends BaseController {

	@Autowired
	private ScreenService screenService;
	
	@ModelAttribute
	public Screen get(@RequestParam(required=false) String id) {
		Screen entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = screenService.get(id);
		}
		if (entity == null){
			entity = new Screen();
		}
		return entity;
	}
	
	/**
	 * 屏幕控制列表页面
	 */
	@RequiresPermissions("screen:screen:list")
	@RequestMapping(value = {"list", ""})
	public String list(Screen screen, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Screen> page = screenService.findPage(new Page<Screen>(request, response), screen); 
		model.addAttribute("page", page);
		return "modules/screen/screenList";
	}

	/**
	 * 查看，增加，编辑屏幕控制表单页面
	 */
	@RequiresPermissions(value={"screen:screen:view","screen:screen:add","screen:screen:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Screen screen, Model model) {
		model.addAttribute("screen", screen);
		return "modules/screen/screenForm";
	}

	/**
	 * 保存屏幕控制
	 */
	@RequiresPermissions(value={"screen:screen:add","screen:screen:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Screen screen, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, screen)){
			return form(screen, model);
		}
		if(!screen.getIsNewRecord()){//编辑表单保存
			Screen t = screenService.get(screen.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(screen, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			screenService.save(t);//保存
		}else{//新增表单保存
			screenService.save(screen);//保存
		}
		addMessage(redirectAttributes, "保存屏幕控制成功");
		return "redirect:"+Global.getAdminPath()+"/screen/screen/?repage";
	}
	
	/**
	 * 删除屏幕控制
	 */
	@RequiresPermissions("screen:screen:del")
	@RequestMapping(value = "delete")
	public String delete(Screen screen, RedirectAttributes redirectAttributes) {
		screenService.delete(screen);
		addMessage(redirectAttributes, "删除屏幕控制成功");
		return "redirect:"+Global.getAdminPath()+"/screen/screen/?repage";
	}
	
	/**
	 * 批量删除屏幕控制
	 */
	@RequiresPermissions("screen:screen:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			screenService.delete(screenService.get(id));
		}
		addMessage(redirectAttributes, "删除屏幕控制成功");
		return "redirect:"+Global.getAdminPath()+"/screen/screen/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("screen:screen:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Screen screen, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "屏幕控制"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Screen> page = screenService.findPage(new Page<Screen>(request, response, -1), screen);
    		new ExportExcel("屏幕控制", Screen.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出屏幕控制记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/screen/screen/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("screen:screen:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Screen> list = ei.getDataList(Screen.class);
			for (Screen screen : list){
				try{
					screenService.save(screen);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条屏幕控制记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条屏幕控制记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入屏幕控制失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/screen/screen/?repage";
    }
	
	/**
	 * 下载导入屏幕控制数据模板
	 */
	@RequiresPermissions("screen:screen:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "屏幕控制数据导入模板.xlsx";
    		List<Screen> list = Lists.newArrayList(); 
    		new ExportExcel("屏幕控制数据", Screen.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/screen/screen/?repage";
    }
	
	
	

}