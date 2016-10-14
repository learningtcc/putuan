package com.advanpro.putuan.web.boss;

import com.advanpro.putuan.service.AccessTokenService;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.web.common.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 作者： Vance
 * 时间： 2016/7/29
 * 描述： ${todo}.
 */
@Controller
public class SettingController extends BaseController{

    @Autowired
    private AccessTokenService accessTokenService;

    @RequestMapping("/boss/setting/get")
    @ResponseBody
    public JsonResult getAccessToken() {
        String accessToken = accessTokenService.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            accessTokenService.updateAccessToken();
        }
        return new JsonResult(true, "操作成功").addData("accessToken", accessToken);
    }

    @RequestMapping(value = "/boss/setting/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateAccessToken() {
        try {
            accessTokenService.updateAccessToken();
            return new JsonResult(true, "操作成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new JsonResult(false, "更新设备信息失败");
        }
    }
}
