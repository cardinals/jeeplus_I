package com.jeeplus.modules.interfaces.api.web;


import com.google.common.collect.Maps;
import com.jeeplus.modules.screen.entity.Screen;
import com.jeeplus.modules.screen.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jeeplus.modules.interfaces.api.base.JsonResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value="{apiPath}/screen")
public class ScreenApiController extends ApiBaseController{

    @Autowired
    private ScreenService ss;
    /**
     * 获取屏幕信息列表
     * @return
     */
    @RequestMapping(value="getScreens", method= RequestMethod.POST)
    @ResponseBody
    public JsonResult getScreens(){
        Map result = Maps.newHashMap();

        Screen screen = new Screen();
       Map screenMap = Maps.newHashMap();
       screenMap.put("id",screen.getId());
       screenMap.put("type",screen.getType());
       screenMap.put("area",screen.getArea());
       screenMap.put("article",screen.getArticle());

        return new JsonResult(result);
    }

}


