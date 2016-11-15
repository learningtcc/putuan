package com.advanpro.putuan.api.controller;

import com.advanpro.putuan.api.common.UserTokenWrapper;
import com.advanpro.putuan.api.form.UserFaceVo;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.api.form.UserVo;
import com.advanpro.putuan.service.SendSmsService;
import com.advanpro.putuan.service.UserService;
import com.advanpro.putuan.utils.cache.CacheKey;
import com.advanpro.putuan.utils.check.CheckUtils;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.utils.json.JsonUtils;
import com.advanpro.putuan.utils.json.StatusCode;
import com.advanpro.putuan.utils.token.TokenProcessor;
import com.advanpro.putuan.utils.upload.UploadFile;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/9/9
 * 描述： 用户相关API.
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @Autowired
    private SendSmsService sendSmsService;

    @Autowired
    private TokenProcessor tokenProcessor;

    /**
     * 用户注册
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResult register(@RequestBody UserVo userVo) {
        try {
            User user = userVo.toUser();
            String verifyCode = userVo.getVerifyCode();
            if (!CheckUtils.checkMobile(user.getAccount()))
                return new JsonResult(StatusCode.INPUT_PHONE);
            if (user.getPassword() == null)
                return new JsonResult(StatusCode.INVALID_PARAMETER).setMsg("请输入密码");
            if (userService.getByAccount(user.getAccount()) != null)
                return new JsonResult(StatusCode.USER_ALREADY_EXISTS);

            StatusCode statusCode = checkVerifyCode(user.getAccount(), verifyCode, true);  // 检查验证码
            if (statusCode != StatusCode.OK) {
                return new JsonResult(statusCode);
            }

            user.setPhone(user.getAccount());

            userService.register(user, userVo.getKneelCount(), userVo.getDeviceId());

            return new JsonResult(StatusCode.OK);

        } catch (Exception e) {
            logger.error("用户注册错误：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }


    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult login(HttpServletRequest request, @RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }
            String account = (String) JsonUtils.toMap(param).get("account");
            String password = (String) JsonUtils.toMap(param).get("password");

            User user = userService.getByAccount(account);
            if (user == null)
                return new JsonResult(StatusCode.USER_NOT_EXISTS);

            if (!password.equals(user.getPassword()))
                return new JsonResult(StatusCode.PASSWORD_ERROR);

            String token = tokenProcessor.generateToken(user.getAccount());
            UserTokenWrapper userTokenWrapper = new UserTokenWrapper(user, System.currentTimeMillis());
            cacheService.hSet(CacheKey.PREFIX_NORMAL_USER_TOKEN, token, userTokenWrapper);  // 缓存用户token

            return new JsonResult(StatusCode.OK).addData("token", token).addData("userId", user.getId());
        } catch (Exception e) {
            logger.error("登陆错误：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 退出登录
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public JsonResult logout(HttpServletRequest request, @RequestHeader(value = "token", required = true) String token) {
        try {
            User curUser = getCurrentUser(request);
            if (curUser == null)
                return new JsonResult(StatusCode.NOT_ALLOW);

            UserTokenWrapper userTokenWrapper = (UserTokenWrapper) cacheService.hGet(CacheKey.PREFIX_NORMAL_USER_TOKEN, token);
            if ((null != userTokenWrapper) && userTokenWrapper.isExpire()) {
                cacheService.hdel(CacheKey.PREFIX_NORMAL_USER_TOKEN, token);
            }

            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("退出失败!", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }


    /**
     * 修改用户基本信息
     */
    @ResponseBody
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public JsonResult update(HttpServletRequest request, @RequestBody UserVo userVo) {
        try {

            User user = userVo.toUser();
            User curUser = getCurrentUser(request);
            if (curUser == null || user.getId() != curUser.getId())
                return new JsonResult(StatusCode.NOT_ALLOW);

            userService.updateById(user);
            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("修改用户信息错误：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 用户上传头像
     */
    @ResponseBody
    @RequestMapping(value = "/user/uploadFace", method = RequestMethod.POST)
    public JsonResult uploadUserFace(HttpServletRequest request, UserFaceVo userFaceVo) {
        try {
            int userId = userFaceVo.getUserId();

            User user = getCurrentUser(request);
            if (user == null || user.getId() != userId)
                return new JsonResult(StatusCode.NOT_ALLOW);

            MultipartFile multipartFile = userFaceVo.getFile();
            if (multipartFile == null)
                return new JsonResult(StatusCode.UPLOAD_FILE_NOT_NULL);

            if (!UploadFile.isPicture(multipartFile.getOriginalFilename()))
                return new JsonResult(StatusCode.USER_FACE_NOT_PICTRUE);
            userService.uploadFace(userId, multipartFile);
            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("上传用户头像出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }


    /**
     * 获取用户信息
     */
    @ResponseBody
    @RequestMapping(value = "/user/get")
    public JsonResult getUser(HttpServletRequest request, @RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }
            int userId = Integer.valueOf(JsonUtils.toMap(param).get("userId").toString());

            User user = getCurrentUser(request);
            if (user == null || user.getId() != userId)
                return new JsonResult(StatusCode.NOT_ALLOW);

            UserVo userVo = new UserVo();
            userVo.populateUser(user);
            return new JsonResult(StatusCode.OK).addData("user", userVo);
        } catch (Exception e) {
            logger.error("查询用户信息出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 用户修改密码
     */
    @ResponseBody
    @RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
    public JsonResult updatePassword(HttpServletRequest request, @RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }

            int userId = Integer.valueOf(JsonUtils.toMap(param).get("userId").toString());
            String password = (String) JsonUtils.toMap(param).get("password");
            String newPassword = (String) JsonUtils.toMap(param).get("newPassword");

            User user = getCurrentUser(request);
            if (user == null || user.getId() != userId)
                return new JsonResult(StatusCode.NOT_ALLOW);

            if (!user.getPassword().equals(password))
                return new JsonResult(StatusCode.OLD_PASSWORD_ERROR);


            userService.updatePassword(userId, newPassword);
            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("修改密码出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 忘记密码
     */
    @ResponseBody
    @RequestMapping(value = "/user/forgetPassword", method = RequestMethod.POST)
    public JsonResult forgetPassword(HttpServletRequest request, @RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }

            String account = (String) JsonUtils.toMap(param).get("account");
            String password = (String) JsonUtils.toMap(param).get("password");
            String rePassword = (String) JsonUtils.toMap(param).get("rePassword");
            String verifyCode = (String) JsonUtils.toMap(param).get("verifyCode");

            User user = userService.getByAccount(account);
            if (user == null) {
                return new JsonResult(StatusCode.USER_NOT_EXISTS);
            }

            if (!password.equals(rePassword)) {
                return new JsonResult(StatusCode.REPASSWORD_ERROR);
            }

            StatusCode statusCode = checkVerifyCode(account, verifyCode, true);  // 检查验证码
            if (statusCode != StatusCode.OK) {
                return new JsonResult(statusCode);
            }


            userService.updatePassword(user.getId(), password);
            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("修改密码出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 绑定手机
     */
    @ResponseBody
    @RequestMapping(value = "/user/bindPhone", method = RequestMethod.POST)
    public JsonResult bindPhone(HttpServletRequest request, @RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }

            int userId = Integer.valueOf(JsonUtils.toMap(param).get("userId").toString());
            String phone = (String) JsonUtils.toMap(param).get("phone");
            String verifyCode = (String) JsonUtils.toMap(param).get("verifyCode");

            User user = getCurrentUser(request);
            if (user == null || user.getId() != userId)
                return new JsonResult(StatusCode.NOT_ALLOW);

            StatusCode statusCode = checkVerifyCode(phone, verifyCode, true);  // 检查验证码
            if (statusCode != StatusCode.OK) {
                return new JsonResult(statusCode);
            }

            List<User> userList = userService.queryByPhone(phone);
            if (userList != null && !userList.isEmpty()) {
                return new JsonResult(StatusCode.PHONE_ALREADY_BIND);
            }

            userService.bindPhone(userId, phone);
            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("绑定手机出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 绑定微信用户
     */
    @ResponseBody
    @RequestMapping(value = "/user/bind", method = RequestMethod.POST)
    public JsonResult bindUser(HttpServletRequest request, @RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }

            int userId = Integer.valueOf(JsonUtils.toMap(param).get("userId").toString());
            String openId = (String) JsonUtils.toMap(param).get("openId");
            String nickName = (String) JsonUtils.toMap(param).get("nickName");
            String headimgUrl = (String) JsonUtils.toMap(param).get("headimgUrl");

            User user = getCurrentUser(request);
            if (user == null || user.getId() != userId)
                return new JsonResult(StatusCode.NOT_ALLOW);

            User wxUser = userService.getUserByOpenId(openId);
            if (wxUser != null && wxUser.getUserId() > 0) {
                return new JsonResult(StatusCode.WX_BIND);
            }
            if (wxUser == null || wxUser.getId() == 0) {
                wxUser = new User();
                wxUser.setNickName(nickName);
                wxUser.setOpenId(openId);
                wxUser.setHeadimgUrl(headimgUrl);
                wxUser.setStatus(1);
                wxUser.setUserType("WX");
            }
            userService.bindUser(wxUser, user);
            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("绑定微信出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 解绑微信用户
     */
    @ResponseBody
    @RequestMapping(value = "/user/unBind", method = RequestMethod.POST)
    public JsonResult unBindUser(HttpServletRequest request, @RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }

            int userId = Integer.valueOf(JsonUtils.toMap(param).get("userId").toString());

            User user = getCurrentUser(request);
            if (user == null || user.getId() != userId)
                return new JsonResult(StatusCode.NOT_ALLOW);

            User wxUser = userService.get(user.getUserId());
            userService.unBindUser(wxUser, user);
            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("绑定微信出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 检查微信是否绑定
     */
    @ResponseBody
    @RequestMapping(value = "/user/checkBind", method = RequestMethod.POST)
    public JsonResult checkUserBind(HttpServletRequest request, @RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }

            int userId = Integer.valueOf(JsonUtils.toMap(param).get("userId").toString());
            //String openId = (String) JsonUtils.toMap(param).get("openId");

            User user = getCurrentUser(request);
            if (user == null || user.getId() != userId)
                return new JsonResult(StatusCode.NOT_ALLOW);

            int wxUserId = user.getUserId();
            User wxUser = userService.get(wxUserId);
            if (wxUser != null) {
                return new JsonResult(StatusCode.OK).addData("nickName", wxUser.getNickName());
            }

            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("检查微信号出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }


    /**
     * 获取验证码
     */
    @ResponseBody
    @RequestMapping(value = "/getVerifyCode")
    public JsonResult getVerifyCode(@RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }
            String account = (String) JsonUtils.toMap(param).get("account");
            String randomCode = RandomStringUtils.randomNumeric(6);
            if (CheckUtils.checkMobile(account)) {
                try {
                    // 发短信
                    sendSmsService.sendVerifyCode(account, randomCode);
                } catch (Exception e) {
                    return new JsonResult(StatusCode.NOT_ALLOW).setMsg(e.getMessage());
                }
            } else {
                return new JsonResult(StatusCode.INPUT_PHONE);
            }

            // 验证码10分中之后过期
            cacheService.put(CacheKey.phoneVerifyCode(account), randomCode, 60 * 10);
            return new JsonResult(StatusCode.OK).addData("code", randomCode);
        } catch (Exception e) {
            logger.error("获取验证码出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 检查手机号是否注册
     */
    @ResponseBody
    @RequestMapping(value = "/checkAccount", method = RequestMethod.POST)
    public JsonResult checkAccount(@RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }
            String account = (String) JsonUtils.toMap(param).get("account");
            List<User> userList = userService.queryByAccount(account);
            if (userList != null && !userList.isEmpty()) {
                return new JsonResult(StatusCode.PHONE_ALREADY_EXISTS);
            }

            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("检查手机号出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 检查验证码的正确性
     */
    @ResponseBody
    @RequestMapping(value = "/checkVerifyCode", method = RequestMethod.POST)
    public JsonResult checkVerifyCode(@RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }
            String account = (String) JsonUtils.toMap(param).get("account");
            String verifyCode = (String) JsonUtils.toMap(param).get("verifyCode");

            StatusCode statusCode = checkVerifyCode(account, verifyCode, false);  // 检查验证码
            return new JsonResult(statusCode);
        } catch (Exception e) {
            logger.error("检查验证码出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

}
