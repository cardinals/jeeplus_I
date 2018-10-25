package com.jeeplus.modules.business.api.web;


import com.google.common.collect.Maps;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.api.annotation.Json;
import com.jeeplus.modules.business.api.base.JsonResult;
import com.jeeplus.modules.business.api.web.ApiBaseController;
import com.jeeplus.modules.screen.entity.Screen;
import com.jeeplus.modules.screen.service.ScreenService;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="{apiPath}/screen")
public class ScreenApiController extends ApiBaseController {

    @Autowired
    private ScreenService screenService;
    /**
     * 获取屏幕播放信息
     * @return
     */
    @RequestMapping(value="getScreens", method= RequestMethod.POST)
    @ResponseBody
    public JsonResult getScreens(@RequestParam(required = false) String id){
       Screen info = screenService.get(id);
        Map map = Maps.newHashMap();
        map.put("remarks",info.getRemarks());
        map.put("type",info.getType());
        map.put("area",info.getArea().getName());
        map.put("artical",info.getArticle());
        map.put("logo",info.getLogo());
        map.put("backgroudphoto",info.getBackgroundPhoto());
        map.put("photoanvideo",info.getPhotoAndVideo());

        return new JsonResult(map);
    }

}



