package com.advanpro.putuan.web.wechat.helper;

import com.advanpro.putuan.model.BaseKneelInfo;
import com.advanpro.putuan.model.Device;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.type.DeviceType;
import com.advanpro.putuan.service.*;
import com.advanpro.putuan.task.AccessTokenScheduled;
import com.advanpro.putuan.utils.consts.MessageHandlerUtil;
import com.advanpro.putuan.utils.date.DateUtils;
import com.advanpro.putuan.utils.wx.MpApi;
import com.advanpro.putuan.utils.wx.MpProperty;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static com.advanpro.putuan.model.type.DeviceType.KD;
import static com.advanpro.putuan.model.type.DeviceType.PT;
import static com.advanpro.putuan.model.type.DeviceType.ST;

/**
 * 作者： Joinly
 * 时间： 2016/7/8
 * 描述： ${todo}.
 */
public class MessageProcessHelper {

    private static Logger logger = LoggerFactory.getLogger(AccessTokenScheduled.class);

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private MpProperty mpProperty;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDeviceService userDeviceService;

    @Autowired
    private KneelInfoService kneelInfoService;

    @Autowired
    private DeviceService deviceService;

    /**
     * 关注公众号
     **/
    public String processSubscribe(Map<String, String> map) {
        String openId = map.get("FromUserName");
        Map<String, String> param = new HashMap<>();
        //accessTokenService.updateAccessToken();
        System.out.println("===accessTokenService.getAccessToken()===========================================" + accessTokenService.getAccessToken());
        param.put("access_token", accessTokenService.getAccessToken());
        param.put("openid", openId);
        param.put("lang", "zh_CN");

        //获取用户信息
        Map result = MpApi.get(mpProperty.getMpUserInfoUrl(), param, Map.class);
        User user = User.parse(result);
        user.setUserType("WX");
        User dbUser = userService.getUserByOpenId(openId);
        if (null == dbUser) {
            userService.add(user);
        } else {
            user.setStatus(1);
            userService.update(user);
        }
        String content = "非常感谢您对安润普的支持与关注。安润普基于柔性传感技术的智能可穿戴产品将会与您携手体验更精彩的智能生活。";
        return MessageHandlerUtil.handleEventMessage(map, content);
    }

    /**
     * 取消关注公众号
     **/
    public String processUnSubscribe(Map<String, String> map) {
        String openId = map.get("FromUserName");
        Map<String, String> param = new HashMap<>();
        param.put("access_token", accessTokenService.getAccessToken());
        param.put("openid", openId);
        param.put("lang", "zh_CN");

        //获取用户信息
        Map result = MpApi.get(mpProperty.getMpUserInfoUrl(), param, Map.class);
        userService.delete(openId);
        String content = "取消关注。";
        return content;
    }

    public String processScan(Map<String, String> map) {
        String deviceId = map.get("EventKey");
        String openId = map.get("FromUserName");
        // userDeviceService.bindDeviceWX(openId, deviceId);
        String content = "";
        return content;
    }

    public String processUnBind(Map<String, String> map) {
        String deviceId = map.get("DeviceID");
        String openId = map.get("OpenID");
        String typeCode = deviceService.queryByDeviceId(deviceId).getTypeCode();
        String deviceType = DeviceType.valueOf(typeCode).desc();
        String content = deviceType + "解绑成功!";
        userDeviceService.unBindDeviceWX(openId, deviceId);
        return MessageHandlerUtil.handleEventMessage(map, content);
    }

    public String processBind(Map<String, String> map) {
        String deviceId = map.get("DeviceID");
        String openId = map.get("OpenID");
        String typeCode = deviceService.queryByDeviceId(deviceId).getTypeCode();
        String deviceType = DeviceType.valueOf(typeCode).desc();
        String content = deviceType + "绑定成功!";
        User user = userService.getUserByOpenId(openId);
        if (StringUtils.isEmpty(user.getPhone())) {
            content = "请先绑定手机!";
        } else {
            userDeviceService.bindDeviceWX(openId, deviceId);
        }
        return MessageHandlerUtil.handleEventMessage(map, content);
    }

    /**
     * 处理设备上传的数据
     *
     * @param map
     * @return
     */
    public String processDeviceMessage(Map<String, String> map) {
        String openId = map.get("OpenID");
        String deviceId = map.get("DeviceID");
        byte[] content = Base64.decodeBase64(map.get("content"));
        if (content.length == 6) {
            int kneelCount = ((content[4] & 0x000000FF) << 8) + (content[5] & 0x000000FF);
            User user = userService.getBindUserByOpenId(openId);
            kneelInfoService.addOrUpdateWX(user.getId(), deviceId, kneelCount);
        }
        if (content.length == 4) {
            if (content[0] == 0x02 && content[1] == 0x04) {
                if (content[3] != 0x01) {
                    logger.error("清除设备数据出错, Debug Code:" + content[4]);
                }
            }
        }

        return "";
    }
}
