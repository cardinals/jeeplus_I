package com.jeeplus.modules.business.api.web;


import com.google.common.collect.Maps;
import com.jeeplus.modules.business.api.annotation.Json;
import com.jeeplus.modules.business.api.base.JsonResult;
import com.jeeplus.modules.tb_facility.entity.Facility;
import com.jeeplus.modules.tb_facility.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "{apiPath}/facility")
public class FacilityApiController extends ApiBaseController {

    @Autowired
    private FacilityService fs;

    /**
     * 查询设备列表
     * @return
     */
    @RequestMapping(value = "getFacilityList",method= RequestMethod.POST)
    @Json(type = Facility.class,include = "id,name,number")
    public JsonResult getFacilityList(){
            List<Facility> list  =fs.findList(new Facility());

        return  new JsonResult(list);
    }

    /**
     * 获取设备配置信息
     */
     @RequestMapping(value = "getFacilityParameter",method = RequestMethod.POST)
     @ResponseBody
    public  JsonResult getFacilityParameter(){
         Map result = Maps.newHashMap();

         Facility facility =  new Facility();

         Map facilityMap = Maps.newHashMap();
         facilityMap.put("id",facility.getId());
         facilityMap.put("user",facility.getUser());
         facilityMap.put("area",facility.getArea());
         facilityMap.put("type",facility.getType());

        return new JsonResult(result);
    }
}
