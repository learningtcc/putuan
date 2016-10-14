package com.advanpro.putuan.web.boss;

import com.advanpro.putuan.dao.query.UserQuery;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.service.KneelInfoService;
import com.advanpro.putuan.service.UserService;
import com.advanpro.putuan.utils.common.Page;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 作者： Vance
 * 时间： 2016/9/1
 * 描述： ${todo}.
 */
@Controller
public class UserBossController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private KneelInfoService kneelInfoService;

    @ResponseBody
    @RequestMapping("/boss/user/list")
    public JsonResult queryUser(UserQuery userQuery) {
        try {

            Page<User> page = userService.queryUserByCondition(userQuery);
            return new JsonResult(true).addData("totalCount", page.getTotalCount()).addData("result", page.getResult());
        } catch (Exception e) {
            logger.error("查询用户列表出错:", e);
            return new JsonResult(false, "查询失败!");
        }

    }

    @RequestMapping(value = "/boss/user/{id}")
    @ResponseBody
    public JsonResult queryById(@PathVariable("id") int id) {
        try {
            User user = userService.get(id);
            return new JsonResult(true, "操作成功!").addData("User", user);
        } catch (Exception e) {
            logger.error("查询用户出错", e);
            return new JsonResult(false, "查询失败!");
        }
    }

    @RequestMapping(value = "/boss/user/count")
    @ResponseBody
    public JsonResult count() {
        try {
            int userCount = userService.getUserCount();
            int kneelCount  = kneelInfoService.getKneelCount();
            return new JsonResult(true, "操作成功!").addData("registerCount", userCount).addData("kneelCount", kneelCount);
        } catch (Exception e) {
            logger.error("查询用户总数出错", e);
            return new JsonResult(false, "查询失败!");
        }
    }
}
