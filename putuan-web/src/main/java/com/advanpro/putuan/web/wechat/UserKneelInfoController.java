package com.advanpro.putuan.web.wechat;

import com.advanpro.putuan.model.KneelInfo;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.UserKneelInfo;
import com.advanpro.putuan.service.KneelInfoService;
import com.advanpro.putuan.service.UserService;
import com.advanpro.putuan.utils.date.DateUtils;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.utils.json.JsonUtils;
import com.advanpro.putuan.utils.json.StatusCode;
import com.advanpro.putuan.utils.wx.MpApi;
import com.advanpro.putuan.utils.wx.MpProperty;
import com.advanpro.putuan.web.common.BaseController;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 作者： Vance
 * 时间： 2016/9/12
 * 描述： ${todo}.
 */
@Controller
public class UserKneelInfoController extends BaseController {

    @Autowired
    private KneelInfoService kneelInfoService;

    @Autowired
    private MpProperty mpProperty;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/wx/my")
    public JsonResult getKneelInfo(String openId, String province) {
        try {
            User user = userService.getBindUserByOpenId(openId);
            Date beginTime = DateUtils.getDateStart(DateUtils.getCurrentDate());
            Date endTime = DateUtils.getDateStart(DateUtils.addDays(DateUtils.getCurrentDate(), 1));
            UserKneelInfo userKneelInfo = kneelInfoService.getUserKneelInfo(user, province, beginTime, endTime);
            return new JsonResult(true).addData("result", userKneelInfo);
        } catch (Exception e) {
            logger.error("获取用户跪拜数据出错:", e);
            return new JsonResult(false, "获取用户跪拜数据出错");
        }
    }

    @ResponseBody
    @RequestMapping("/wx/kneels")
    public JsonResult getKneelInfoList(String openId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginTime,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {
        try {
            User user = userService.getBindUserByOpenId(openId);
            List<KneelInfo> kneelInfoList = kneelInfoService.queryByUserIdAndTime(user.getId(), beginTime, endTime);
            return new JsonResult(true).addData("totalCount", kneelInfoList.size()).addData("result", kneelInfoList);
        } catch (Exception e) {
            logger.error("获取用户历史跪拜数据出错:", e);
            return new JsonResult(false, "获取用户历史跪拜数据出错");
        }
    }

    @ResponseBody
    @RequestMapping("/wx/kneel/ranking")
    public JsonResult getRanking(String province) {
        try {

            Date beginTime = DateUtils.getDateStart(DateUtils.getCurrentDate());
            Date endTime = DateUtils.getDateStart(DateUtils.addDays(DateUtils.getCurrentDate(), 1));
            List<UserKneelInfo> userKneelInfoList = kneelInfoService.getKneelInfoRanking(province, beginTime, endTime);
            if (userKneelInfoList.size() > 7) {
                userKneelInfoList = userKneelInfoList.subList(0, 7);
            }

            return new JsonResult(true).addData("ranking", userKneelInfoList);
        } catch (Exception e) {
            logger.error("查询用户跪拜排名出错:", e);
            return new JsonResult(false, "查询用户跪拜排名出错");
        }

    }

    @ResponseBody
    @RequestMapping("/wx/sethistory")
    public JsonResult setHistory(String openId, int kneelCount) {
        try {
            User user = userService.getBindUserByOpenId(openId);
            if (user.getHistoried() != 0) {
                return new JsonResult(false, "已经在微信或APP录入了历史数据");
            }
            KneelInfo kneelInfo = new KneelInfo();
            kneelInfo.setUserId(user.getId());
            kneelInfo.setKneelCount(kneelCount);
            kneelInfo.setStartTime(DateUtils.addDays(user.getCreateTime(), -1));
            kneelInfoService.setHistory(user, kneelInfo);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("录入历史数据出错:", e);
            return new JsonResult(false, "录入历史数据出错");
        }
    }

    @ResponseBody
    @RequestMapping("/wx/checkhistory")
    public JsonResult checkHistory(String openId) {
        try {
            User user = userService.getBindUserByOpenId(openId);
            if (user.getHistoried() != 0) {
                return new JsonResult(false, "已经在微信或APP录入了历史数据");
            }
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("检测录入历史数据出错:", e);
            return new JsonResult(false, "检测录入历史数据出错");
        }
    }

}
