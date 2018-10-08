package com.jeeplus.modules.interfaces.api.web;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.Encodes;
import com.jeeplus.common.web.BaseController;
/*import com.jeeplus.modules.business.api.annotation.Json;
import com.jeeplus.modules.business.api.base.JsonResult;
import com.jeeplus.modules.homebroadcast.entity.Homebroadcast;
import com.jeeplus.modules.homebroadcast.service.HomebroadcastService;*/
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.jeeplus.modules.interfaces.api.base.JsonResult;
import com.jeeplus.modules.interfaces.api.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping(value = "${apiPath}")
public class ApiBaseController extends BaseController {
    /*@Autowired
    private HomebroadcastService hs;
*/
    /**
     * 统一的异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult exception(Exception ex) {
        ex.printStackTrace();
        return new JsonResult(ex);
    }

    /**
     * 登录状态检查
     * @return
     */
    @RequestMapping(value = "loginCheck")
    @Json
    public JsonResult getRoutes() {
        return new JsonResult();
    }

    /**
     * 获取首页轮播图
     * @return
     *//*
    @RequestMapping(value = "getCarousel")
    @Json(type = Homebroadcast.class,include = "title,files")
    public JsonResult getCarousel() {
        List<Homebroadcast> list = hs.findList(new Homebroadcast());
        return new JsonResult(list);
    }
*/


    /**
     * 文件上传
     * @param uploadfile
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "fileupload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getDictList(MultipartFile uploadfile, HttpServletRequest request) throws IOException {
        if (uploadfile == null || uploadfile.getSize() <= 0)
            throw new RuntimeException("文件为空");

        String fileName = uploadfile.getOriginalFilename();
        if (fileName.endsWith("jsp") || fileName.endsWith("php") || fileName.endsWith("asp") || fileName.endsWith("bat") || fileName.endsWith("exe")) {
            throw new RuntimeException("禁止上传的文件类型!");
        }

        User user = UserUtils.getUser();
        String baseDir = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL + user.getId() + "/files/api";

        if (!new File(baseDir).exists()) {
            new File(baseDir).mkdirs();
        }

        File file;
        int index = 0;
        String newfileName = fileName;
        do {
            file = new File(baseDir, newfileName);
            newfileName = fileName + "(" + ++index + ")";
        } while (file.exists());
        uploadfile.transferTo(file);
        return new JsonResult("/userfiles/" + user.getId() + "/files/api/" + Encodes.urlEncode(index > 1 ? fileName + "(" + --index + ")" : fileName).replaceAll("\\+", "%20"));
    }
}
