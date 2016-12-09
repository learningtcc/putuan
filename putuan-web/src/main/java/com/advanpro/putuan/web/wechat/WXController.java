package com.advanpro.putuan.web.wechat;

import com.advanpro.putuan.model.User;
import com.advanpro.putuan.service.AccessTokenService;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.utils.wx.MpApi;
import com.advanpro.putuan.utils.wx.MpProperty;
import com.advanpro.putuan.web.common.BaseController;
import com.advanpro.putuan.web.form.UserVo;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 作者： Vance
 * 时间： 2016/10/11
 * 描述： ${todo}.
 */
@Controller
public class WXController extends BaseController {

    @Autowired
    private MpProperty mpProperty;

    @Autowired
    private AccessTokenService accessTokenService;


    /**
     * accessToken
     *
     * @param mv
     * @return
     */
    @RequestMapping("/wx/accessToken")
    public ModelAndView accessToken(ModelAndView mv) {
        String accessToken = accessTokenService.getAccessToken();
        if (StringUtils.isBlank(accessToken)) {
            accessTokenService.updateAccessToken();
            accessToken = accessTokenService.getAccessToken();
        }
        mv.addObject("accessToken", accessToken);
        mv.setViewName("/wx/token");
        return mv;
    }

    /**
     * 二维码
     *
     * @param mv
     * @return
     */
    @RequestMapping("/wx/qrcode")
    public ModelAndView index(ModelAndView mv) {
        String accessToken = accessTokenService.getAccessToken();
        //获取ticket
        String params = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"1234\"}}}";
        Map map = MpApi.postJson("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken, params, Map.class);
        mv.addObject("ticket", map.get("ticket"));
        mv.addObject("url", map.get("url"));
        mv.setViewName("/wx/qrcode");
        return mv;
    }

    /**
     * 微信首页入口
     *
     * @param mv
     * @return
     */
    @RequestMapping("/wx")
    public ModelAndView index(String code, ModelAndView mv) {
        Map params = Maps.newHashMap();
        params.put("appid", mpProperty.getAppId());
        params.put("secret", mpProperty.getAppSecret());
        params.put("code", code);
        params.put("grant_type", "authorization_code");

        Map map = MpApi.get(mpProperty.getWebAccessTokenUrl(), params, Map.class);
        String openId = String.valueOf(map.get("openid"));
        mv.addObject("openId", openId);
        mv.setViewName("/wx/index");
        return mv;
    }


    /**
     * 微信排行入口
     *
     * @return
     */
    @RequestMapping("/wx/ranking")
    public ModelAndView ranking(String code) {
        ModelAndView mv = new ModelAndView("redirect:/wx#!/ranking");
        mv.addObject("code", code);
        return mv;
    }

    /**
     * 微信录入历史数据入口
     *
     * @return
     */
    @RequestMapping("/wx/history")
    public ModelAndView history(String code) {
        ModelAndView mv = new ModelAndView("redirect:/wx#!/history");
        mv.addObject("code", code);
        return mv;
    }

    /**
     * 微信设备管理入口
     *
     * @return
     */
    @RequestMapping("/wx/devices")
    public ModelAndView devices(String code) {
        ModelAndView mv = new ModelAndView("redirect:/wx#!/devices");
        mv.addObject("code", code);
        return mv;
    }

    /**
     * 微信绑定APP入口
     *
     * @return
     */
    @RequestMapping("/wx/bind")
    public ModelAndView bind(String code) {
        ModelAndView mv = new ModelAndView("redirect:/wx#!/bind");
        mv.addObject("code", code);
        return mv;
    }

}
