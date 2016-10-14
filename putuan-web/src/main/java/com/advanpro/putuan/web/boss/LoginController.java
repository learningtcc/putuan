package com.advanpro.putuan.web.boss;

import com.advanpro.putuan.model.Account;
import com.advanpro.putuan.service.AccountService;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者： Joinly
 * 时间： 2016/7/21
 * 描述： 登陆.
 */

@Controller
public class LoginController extends BaseController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/login")
    public ModelAndView login(ModelAndView mv) {
        mv.setViewName("/boss/login");
        return mv;
    }

    @RequestMapping("/tologin")
    @ResponseBody
    public JsonResult toLogin(HttpServletRequest request, String userName, String password) {
        try {
            Account account = accountService.getAccountByUserName(userName);
            if (null == account) {
                return new JsonResult(false, "用户不存在");
            }
            if (!account.getPassword().equals(password)) {
                return new JsonResult(false, "密码不正确");
            }
            doLogin(request, account);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("登陆错误：", e);
        }
        return new JsonResult(false, "服务器内部错误");
    }

    @RequestMapping("/logout")
    @ResponseBody
    public JsonResult logout(HttpServletRequest request) {
        doLogout(request);
        return new JsonResult(true);
    }
}
