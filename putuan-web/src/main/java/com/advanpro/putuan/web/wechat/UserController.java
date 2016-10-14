package com.advanpro.putuan.web.wechat;

import com.advanpro.putuan.model.Device;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.UserDevice;
import com.advanpro.putuan.service.AccessTokenService;
import com.advanpro.putuan.service.SendSmsService;
import com.advanpro.putuan.service.UserService;
import com.advanpro.putuan.utils.cache.CacheKey;
import com.advanpro.putuan.utils.cache.CacheService;
import com.advanpro.putuan.utils.check.CheckUtils;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.utils.json.JsonUtils;
import com.advanpro.putuan.utils.json.StatusCode;
import com.advanpro.putuan.utils.wx.MpApi;
import com.advanpro.putuan.utils.wx.MpProperty;
import com.advanpro.putuan.web.common.BaseController;
import com.advanpro.putuan.web.form.UserVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * 作者： Joinly
 * 时间： 2016/7/7
 * 描述： ${todo}.
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private SendSmsService sendSmsService;

    @Autowired
    private CacheService cacheService;

    @ResponseBody
    @RequestMapping("/wx/getUser")
    public JsonResult getUser(String openId) {
        try {
            User user = userService.getUserByOpenId(openId);
            UserVo userVo = new UserVo();
            userVo.populateUser(user);
            return new JsonResult(true).addData("user", userVo);
        } catch (Exception e) {
            logger.error("获取用户信息出错:", e);
            return new JsonResult(false, "获取用户信息出错");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/wx/updateUser", method = RequestMethod.POST)
    public JsonResult updateUser(UserVo userVo) {
        try {
            User user = userService.getUserByOpenId(userVo.getOpenId());
            user.setBirthday(userVo.getBirthday());
            user.setAge(userVo.getAge());
            userService.update(user);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("更新用户出错:", e);
            return new JsonResult(false, "更新用户出错");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/wx/bindPhone", method = RequestMethod.POST)
    public JsonResult bindUser(String openId, String phone, String verifyCode) {
        try {
            User wxUser = userService.getUserByOpenId(openId);
            if (wxUser != null && (wxUser.getUserId() != 0 || StringUtils.isNotEmpty(wxUser.getPhone()))) {
                return new JsonResult(false, "已经绑定了手机号");
            }
            User appUser = userService.getByAccount(phone);
            if (!checkVerifyCode(phone, verifyCode, true)) {
                return new JsonResult(false, "验证码不正确");
            }
            if (appUser == null) {
                userService.bindPhone(wxUser.getId(), phone);
            } else {
                userService.bindUser(wxUser, appUser);
            }

            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("绑定用户出错:", e);
            return new JsonResult(false, "绑定用户出错");
        }
    }

    /**
     * 获取验证码
     */
    @ResponseBody
    @RequestMapping(value = "/wx/getVerifyCode")
    public JsonResult getVerifyCode(String phone) {
        try {

            String randomCode = RandomStringUtils.randomNumeric(6);
            if (CheckUtils.checkMobile(phone)) {
                try {
                    // 发短信
                    //sendSmsService.sendVerifyCode(phone, randomCode);
                } catch (Exception e) {
                    return new JsonResult(false, "获取验证码出错");
                }
            } else {
                return new JsonResult(false, "输入正确的手机号码");
            }

            // 验证码10分中之后过期
            cacheService.put(CacheKey.phoneVerifyCode(phone), randomCode, 60 * 10);
            return new JsonResult(true).addData("code", randomCode);
        } catch (Exception e) {
            logger.error("获取验证码出错：", e);
            return new JsonResult(false, "获取验证码出错");
        }
    }

    protected Boolean checkVerifyCode(String account, String verifyCode, boolean remove) {
        if (verifyCode == null || verifyCode.isEmpty())
            return false;
        String cacheKey = CacheKey.phoneVerifyCode(account);
        String randomCode = (String) cacheService.get(cacheKey);  // 从缓存查询验证码
        if (null == randomCode || !randomCode.equals(verifyCode))
            return false;
        if (remove)
            cacheService.remove(cacheKey); // 将验证码设为无效
        return true;
    }

}
