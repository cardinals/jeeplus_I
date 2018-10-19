package com.jeeplus.modules.business.api.web;


import com.google.common.collect.Maps;
import com.jeeplus.modules.business.api.base.JsonResult;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Map;

@Controller
  @RequestMapping(value = "{apiPath}/user")
  public class UserApiController extends ApiBaseController {

    @Autowired

    private SystemService ss;
      /**
       * 个人信息
       */

      @RequestMapping(value = "myinfo",method = RequestMethod.POST)
      @ResponseBody
     public JsonResult getMyInfo(){
          Map result = Maps.newHashMap();

          User user = UserUtils.getUser();
          Map userMap = Maps.newHashMap();
          userMap.put("id",user.getId());
          userMap.put("photo",user.getPhoto());
          userMap.put("name",user.getName());
          userMap.put("mobile",user.getMobile());
          result.put("user",userMap);

          return new JsonResult(result);
     }


    /**人员的增加和修改
     *
     */
     @RequestMapping(value = "addAndUpdateUser",method = RequestMethod.POST)
     @ResponseBody
     public JsonResult addAndUpdateUser(User user){

         if (user.getId()==null){
           ss.saveUser(user);
             return new JsonResult();
         }else {

             ss.updateUserInfo(user);
             return new JsonResult();
         }
     }

    /**
     * 人员的删除
     */
     @RequestMapping(value = "deleteUser",method = RequestMethod.POST)
     @ResponseBody
     public JsonResult  deleteUser(User user){
         ss.deleteUser(user);
         return  new JsonResult();
     }
}
