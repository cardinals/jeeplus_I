package com.jeeplus.modules.business.api.web;

import com.google.common.collect.Maps;
import com.jeeplus.modules.business.api.base.JsonResult;
import com.jeeplus.modules.tb_equipment.entity.IllegalData;
import com.jeeplus.modules.tb_equipment.service.IllegalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
  @RequestMapping(value = "{api}/equipment")
  public class EquipmentApiController {

      @Autowired
      private IllegalDataService is;

      @RequestMapping(value = "getIllegaData",method = RequestMethod.POST)
      @ResponseBody
      public JsonResult getIllegalData(){
          Map result = Maps.newHashMap();

          IllegalData illegalData = new IllegalData();
          Map illegalDataMap = Maps.newHashMap();
          illegalDataMap.put("id",illegalData.getId());
          illegalDataMap.put("name",illegalData.getName());
          illegalDataMap.put("IdCard",illegalData.getIdCard());
          illegalDataMap.put("area",illegalData.getArea());
          illegalDataMap.put("photo",illegalData.getPhotoes());


          return  new JsonResult(result);
      }

  }
