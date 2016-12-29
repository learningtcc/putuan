package com.advanpro.putuan.web.wechat.helper;

import com.advanpro.putuan.model.Device;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.UserDevice;
import com.advanpro.putuan.model.type.DeviceType;
import com.advanpro.putuan.service.*;
import com.advanpro.putuan.utils.consts.MessageHandlerUtil;
import com.advanpro.putuan.utils.wx.MpApi;
import com.advanpro.putuan.utils.wx.MpProperty;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者： Joinly
 * 时间： 2016/7/8
 * 描述： ${todo}.
 */
public class MessageProcessHelper {
    private static Log logger = LogFactory.getLog(MessageProcessHelper.class);

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
        String content = "非常感谢您对大白牛文化科技的支持与关注。";
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
        Device device = deviceService.queryByDeviceId(deviceId);
        String deviceType = DeviceType.valueOf(device.getTypeCode()).desc();
        String content = deviceType + "解绑成功! 设备编号: " + device.getDeviceNumber();
        userDeviceService.unBindDeviceWX(openId, deviceId);
        String accessToken = accessTokenService.getAccessToken();
        String url = mpProperty.getMpMessageCustomSendUrl() + "?access_token=" + accessToken;
        String json = "{\"touser\": \"" + openId + "\", \"msgtype\": \"text\", \"text\": {\"content\": \"" + content + "\"}}";
        Map result = MpApi.postJson(url, json, Map.class);
        logger.debug("设备解绑,向用户发送信息, " + "result: " + result.toString() + ", content: " + content);
        return "";
    }

    /**
     * 绑定设备
     *
     * @param map
     * @return
     */
    public String processBind(Map<String, String> map) {
        String deviceId = map.get("DeviceID");
        String openId = map.get("OpenID");
        Device device = deviceService.queryByDeviceId(deviceId);
        String deviceType = DeviceType.valueOf(device.getTypeCode()).desc();
        User user = userService.getUserByOpenId(openId);
        String content = deviceType + "绑定成功! 设备编号: " + device.getDeviceNumber();
        boolean isBinded = true;

        List<UserDevice> userDeviceList = Lists.newArrayList();
        userDeviceList.addAll(userDeviceService.getDevice(user.getId()));
        userDeviceList.addAll(userDeviceService.getDevice(user.getUserId()));
        for (UserDevice userDevice : userDeviceList) {
            Device otherDevice = deviceService.queryByDeviceId(userDevice.getDeviceId());
            if (device.getTypeCode().equalsIgnoreCase(otherDevice.getTypeCode())) {
                content = "微信或APP同一类型的设备只能绑定一个! ";
                isBinded = false;
            }
        }

        if (isBinded) {
            List<UserDevice> otherUserDeviceList = userDeviceService.queryUsingByDeviceId(deviceId);
            if (otherUserDeviceList != null && !otherUserDeviceList.isEmpty()) {
                for (UserDevice userDevice : otherUserDeviceList) {
                    User otherUser = userService.get(userDevice.getUserId());
                    if ("WX".equalsIgnoreCase(otherUser.getUserType()) && StringUtils.isNotEmpty(otherUser.getOpenId())) {
                        userDeviceService.unBindDeviceWX(otherUser.getOpenId(), deviceId);
                    } else {
                        userDeviceService.unBindDevice(otherUser.getId(), deviceId);
                    }
                }
            }

            if (user != null && StringUtils.isEmpty(user.getPhone())) {
                content += " 请尽快绑定手机!";
            }
            content += "\n注意: 在使用前请确保蓝牙及网络通畅！(如遇连接问题请退出公众号, 再次进入尝试连接)";
            userDeviceService.bindDeviceWX(openId, deviceId);
        } else {
            //不符合绑定逻辑的需要手动解绑
            deviceService.compelUnBind(openId, deviceId);
        }

        String accessToken = accessTokenService.getAccessToken();
        String url = mpProperty.getMpMessageCustomSendUrl() + "?access_token=" + accessToken;
        String json = "{\"touser\": \"" + openId + "\", \"msgtype\": \"text\", \"text\": {\"content\": \"" + content + "\"}}";
        Map result = MpApi.postJson(url, json, Map.class);
        logger.debug("设备绑定, 向用户发送信息, " + "result: " + result.toString() + ", content: " + content);
        return "";
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
        String contentStr = map.get("Content");
        byte[] content = Base64.decodeBase64(contentStr);
        logger.debug("接收到设备发送的信息, Content: " + Arrays.toString(content) + ", Device Id:" + deviceId + ", Open Id: " + openId);
        if (content == null || content.length == 0) {
            return "";
        }
        if (content.length == 6) {
            if (content[0] == 0x0a && content[1] == 0x01) {
                int kneelCount = ((content[4] & 0xFF)) | ((content[5] & 0xFF) << 8);
                User user = userService.getBindUserByOpenId(openId);
                kneelInfoService.addOrUpdateWX(user.getId(), deviceId, kneelCount);
            }
        }
        if (content.length == 4) {
            if (content[0] == 0x0a && content[1] == 0x07) {
                if (content[2] != 0x01) {
                    logger.debug("清除设备数据失败, Debug Code:" + content[3]);
                }
            }
        }

        return "";
    }
}
