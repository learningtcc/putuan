package com.advanpro.putuan.task;

import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.UserDevice;
import com.advanpro.putuan.service.AccessTokenService;
import com.advanpro.putuan.service.UserDeviceService;
import com.advanpro.putuan.service.UserService;
import com.advanpro.putuan.utils.cache.CacheKey;
import com.advanpro.putuan.utils.cache.RedisLock;
import com.advanpro.putuan.utils.date.DateUtils;
import com.advanpro.putuan.utils.wx.MpApi;
import com.advanpro.putuan.utils.wx.MpProperty;
import com.advanpro.putuan.utils.wx.TransMsgUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 作者： Vance
 * 时间： 2016/9/28
 * 描述： 定时通过微信向设备发消息.
 */
@Component
public class TransMsgScheduled {
    private static Log logger = LogFactory.getLog(TransMsgScheduled.class);

    @Autowired
    private MpProperty mpProperty;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDeviceService userDeviceService;

    @Autowired
    private AccessTokenService accessTokenService;

    /**
     * 每分钟向设备发送同步数据的请求
     */
    @Scheduled(fixedRate = 1000 * 60 * 1)
    public void transSyncMsg() {
        logger.info("定时任务[向设备发送同步消息]开启");
        String content = TransMsgUtil.buildSyncMsg();
        List<User> userList = userService.queryWX();
        final String deviceType = mpProperty.getOriginId();
        for (User user : userList) {
            String openId = user.getOpenId();
            List<UserDevice> userDeviceList = userDeviceService.getDevice(user.getId());
            for (UserDevice userDevice : userDeviceList) {
                String deviceId = userDevice.getDeviceId();
                String json = "{\"device_type\": \"" + deviceType + "\", \"device_id\": \"" + deviceId
                        + "\", \"openid\": \"" + openId + "\", \"content\": \"" + content + "\"}";

                String accessToken = accessTokenService.getAccessToken();
                String url = mpProperty.getMpDeviceTransMsgUrl() + "?access_token=" + accessToken;
                Map<String, String> result = MpApi.postJson(url, json, Map.class);
                if (!"0".equals(result.get("errcode"))) {
                    logger.info("定时任务[向设备发送同步消息]出错: " + result.get("errmsg") + ", Device ID: " + deviceId + ", Open ID: " + openId);
                }
            }
        }

        logger.info("定时任务[向设备发送同步消息]结束");
    }

}
