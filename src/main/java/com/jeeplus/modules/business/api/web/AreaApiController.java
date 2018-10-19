package com.jeeplus.modules.business.api.web;

import com.google.common.collect.Maps;
import com.jeeplus.modules.business.api.base.JsonResult;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
  @RequestMapping(value = "{apiPath}/area")
  public class AreaApiController {

      @Autowired
      private AreaService areaService;

      @RequestMapping(value = "areaMessage",method = RequestMethod.POST)
      @ResponseBody
      public JsonResult getAreaMessage(/*@RequestParam String id*/){
         /* Area  info =areaService.get(id);*/
          Map result = Maps.newHashMap();

         /* result.put("name",info.getName());
          result.put("code",info.getCode());
          result.put("type",info.getType());
          result.put("remarks",info.getRemarks());
          result.put("sort",info.getSort());
          result.put("parent",info.getParent());
          result.put("parentIds",info.getParentIds());*/


           Area area = new Area();
          Map areaMap = Maps.newHashMap();
          areaMap.put("name",area.getName());
          areaMap.put("Code",area.getCode());
          areaMap.put("type",area.getType());
          areaMap.put("remarks",area.getRemarks());
          areaMap.put("sort",area.getSort());
          areaMap.put("parent",area.getParent());
          areaMap.put("parentIds",area.getParentIds());

          return new JsonResult(result);


      }


  }