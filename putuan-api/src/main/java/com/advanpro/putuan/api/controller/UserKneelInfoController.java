package com.advanpro.putuan.api.controller;

import com.advanpro.putuan.api.form.KneelInfoVo;
import com.advanpro.putuan.model.KneelInfo;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.UserKneelInfo;
import com.advanpro.putuan.service.KneelInfoService;
import com.advanpro.putuan.utils.date.DateUtils;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.utils.json.JsonUtils;
import com.advanpro.putuan.utils.json.StatusCode;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/9/9
 * 描述： ${todo}.
 */
@Controller
public class UserKneelInfoController extends BaseController {
    @Autowired
    private KneelInfoService kneelInfoService;

    @ResponseBody
    @RequestMapping("/kneel/ranking")
    public JsonResult getRanking(@RequestBody String param) {
        try {

            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }
            String province = (String) JsonUtils.toMap(param).get("province");
            Date beginTime = DateUtils.getDateStart(DateUtils.getCurrentDate());
            Date endTime = DateUtils.getDateStart(DateUtils.addDays(DateUtils.getCurrentDate(), 1));
            List<UserKneelInfo> userKneelInfoList = kneelInfoService.getKneelInfoRanking(province, beginTime, endTime);
            if (userKneelInfoList.size() > 7) {
                userKneelInfoList = userKneelInfoList.subList(0, 7);
            }

            return new JsonResult(StatusCode.OK).addData("kneel", userKneelInfoList);
        } catch (Exception e) {
            logger.error("查询用户跪拜排名出错:", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }

    }

    @ResponseBody
    @RequestMapping("/kneel/my")
    public JsonResult getKneelInfo(@RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }

            int userId = Integer.valueOf(JsonUtils.toMap(param).get("userId").toString());
            String province = (String) JsonUtils.toMap(param).get("province");

            User user = userService.get(userId);
            Date beginTime = DateUtils.getDateStart(DateUtils.getCurrentDate());
            Date endTime = DateUtils.getDateStart(DateUtils.addDays(DateUtils.getCurrentDate(), 1));
            UserKneelInfo userKneelInfo = kneelInfoService.getUserKneelInfo(user, province, beginTime, endTime);
            return new JsonResult(StatusCode.OK).addData("kneel", userKneelInfo);
        } catch (Exception e) {
            logger.error("获取当前用户跪拜数据出错:", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/kneel/upload", method = RequestMethod.POST)
    public JsonResult add(@RequestBody KneelInfoVo kneelInfoVo) {
        try {
            KneelInfo kneelInfo = kneelInfoVo.toKneelInfo();
            kneelInfoService.addOrUpdate(kneelInfo);
            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("上传用户跪拜信息出错", e);
        }
        return new JsonResult(StatusCode.INTERNAL_ERROR);
    }

    @ResponseBody
    @RequestMapping(value = "/kneel/sync", method = RequestMethod.POST)
    public JsonResult sync(@RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }

            int userId = Integer.valueOf(JsonUtils.toMap(param).get("userId").toString());
            long lastSyncTime = Long.valueOf(JsonUtils.toMap(param).get("lastSyncTime").toString());
            List<KneelInfo> kneelInfoList = kneelInfoService.queryByLastUpdate(userId, new Date(lastSyncTime));
            List<KneelInfoVo> kneelInfoVoList = Lists.newArrayList();
            for (KneelInfo kneelInfo : kneelInfoList) {
                KneelInfoVo kneelInfoVo = new KneelInfoVo();
                kneelInfoVo.populateKneelInfo(kneelInfo);
                kneelInfoVoList.add(kneelInfoVo);
            }
            return new JsonResult(StatusCode.OK).addData("list", kneelInfoVoList);
        } catch (Exception e) {
            logger.error("同步用户跪拜信息出错", e);
        }
        return new JsonResult(StatusCode.INTERNAL_ERROR);
    }

}
