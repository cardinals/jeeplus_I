package com.jeeplus.modules.business.api.web;

import com.google.common.collect.Maps;
import com.jeeplus.modules.business.api.base.JsonResult;
import com.jeeplus.modules.tb_equipment.entity.IllegalData;
import com.jeeplus.modules.tb_equipment.service.IllegalDataService;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

  @Controller
  @RequestMapping(value = "{api}/equipment")
  public class EquipmentApiController {

     @Autowired
     private IllegalDataService illegalDataService;

     /**
      * 违法人员列表
      *
      * @param id
      * @return
      */
     @RequestMapping(value = "getIllegalData", method = RequestMethod.POST)
     @ResponseBody
     public JsonResult getIllegalData(@RequestParam(required = false)  String id) {

         IllegalData illegalData = illegalDataService.get(id);
         Map map = Maps.newHashMap();
         map.put("name", illegalData.getName());
         map.put("IdCard", illegalData.getIdCard());
         map.put("area", illegalData.getArea().getName());
         map.put("photo", illegalData.getPhotoes());

         return new JsonResult(map);
     }

     /**
      *保存违法人员数据
      */
     @RequestMapping(value = "saveIllegalData", method = RequestMethod.POST)
     @ResponseBody
     public JsonResult saveIllegalData() {
         IllegalData illegalData = new IllegalData();
         illegalDataService.save(illegalData);

         return new JsonResult();
     }

      /**
       * 删除违法人员数据
       */

      @RequestMapping(value = "deleteIllegalData",method = RequestMethod.POST)
      @ResponseBody
      public JsonResult deleteIllegalData(){
          IllegalData illegalData = new IllegalData();
          illegalDataService.delete(illegalData);
          return  new JsonResult();
      }
  }