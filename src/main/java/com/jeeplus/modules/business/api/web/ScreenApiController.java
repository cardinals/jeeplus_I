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

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="{apiPath}/screen")
public class ScreenApiController extends ApiBaseController {

    @Autowired
    private ScreenService ss;
    /**
     * 获取屏幕播放信息
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



