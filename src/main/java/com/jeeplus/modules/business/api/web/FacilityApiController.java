package com.jeeplus.modules.business.api.web;


import com.google.common.collect.Maps;
import com.jeeplus.modules.business.api.annotation.Json;
import com.jeeplus.modules.business.api.base.JsonResult;
import com.jeeplus.modules.tb_facility.entity.Facility;
import com.jeeplus.modules.tb_facility.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "{apiPath}/facility")
public class FacilityApiController extends ApiBaseController {

    @Autowired
    private FacilityService facilityService;

    /**
     * 查询设备列表
     * @return
     */
    @RequestMapping(value = "getFacilityList",method= RequestMethod.POST)
   @ResponseBody
    public JsonResult getFacilityList(@RequestParam(required = false) String id){
            Facility facility  =facilityService.get(id);
            Map map = Maps.newHashMap();
            map.put("user",facility.getUser().getName());
            map.put("number",facility.getNumber());
            map.put("name",facility.getName());
            map.put("area",facility.getArea().getName());
            map.put("type",facility.getType());

        return  new JsonResult(map);
    }

    /**
     * 获取设备配置信息
     */
     @RequestMapping(value = "getFacilityParameter",method = RequestMethod.POST)
     @ResponseBody
    public  JsonResult getFacilityParameter(@RequestParam(required = false) String id){

         Facility facility =  facilityService.get(id);


         Map facilityMap = Maps.newHashMap();
         facilityMap.put("user",facility.getUser().getName());
         facilityMap.put("area",facility.getArea().getName());
         facilityMap.put("type",facility.getType());

        return new JsonResult(facilityMap);
    }
}
