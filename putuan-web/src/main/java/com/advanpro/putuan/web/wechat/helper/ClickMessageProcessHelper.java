package com.advanpro.putuan.web.wechat.helper;

import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.UserKneelInfo;
import com.advanpro.putuan.model.type.DeviceType;
import com.advanpro.putuan.service.DeviceService;
import com.advanpro.putuan.service.KneelInfoService;
import com.advanpro.putuan.service.UserService;
import com.advanpro.putuan.utils.consts.MessageHandlerUtil;
import com.advanpro.putuan.utils.consts.NewsItem;
import com.advanpro.putuan.utils.date.DateUtils;
import com.advanpro.putuan.utils.wx.MpProperty;
import com.advanpro.putuan.web.common.URLBrokerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * 作者： Joinly
 * 时间： 2016/7/15
 * 描述： ${todo}.
 */
public class ClickMessageProcessHelper {

    @Autowired
    private KneelInfoService kneelInfoService;

    @Autowired
    private URLBrokerFactory urlBrokerFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MpProperty mpProperty;

    private final String url = "https://open.weixin.qq.com/connect/oauth2/authorize";

    public String processMessage(Map<String, String> map) {
        // 根据key值判断点击的哪个菜单
        String eventKey = map.get("EventKey");
        String appId = mpProperty.getAppId();
        String wxHome = urlBrokerFactory.getUrl("WeixinHome").toString();
        if ("V_KNEEL_RANKING".equals(eventKey)) {
            //String redirectUrl = wxHome + "/ranking";
            String redirectUrl = "http://putuan.tunnel.phpor.me/wx/ranking";
            NewsItem item = new NewsItem();
            User user = userService.getBindUserByOpenId(map.get("FromUserName"));
            Date beginTime = DateUtils.getDateStart(DateUtils.getCurrentDate());
            Date endTime = DateUtils.getDateStart(DateUtils.addDays(DateUtils.getCurrentDate(), 1));
            UserKneelInfo allUserKneelInfo = kneelInfoService.getUserKneelInfo(user, "全国", beginTime, endTime);
            UserKneelInfo userKneelInfo = kneelInfoService.getUserKneelInfo(user, user.getProvince(), beginTime, endTime);
            item.setTitle("跪拜排行");
            item.setDescription("跪拜数:                                " + allUserKneelInfo.getKneelCount()
                    + "\n\n全国排名:                            " + allUserKneelInfo.getRanking() + "\n\n"
                    + user.getProvince() + "省排名 :                       " + userKneelInfo.getRanking());
            item.Url = url + "?appid=" + appId + "&redirect_uri=" + redirectUrl + "&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
            return MessageHandlerUtil.buildNewsMessage(map, item);
        }

        return "";
    }

}
