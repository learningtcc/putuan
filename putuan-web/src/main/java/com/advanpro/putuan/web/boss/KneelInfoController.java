package com.advanpro.putuan.web.boss;

import com.advanpro.putuan.dao.query.UserKneelQuery;
import com.advanpro.putuan.model.KneelInfo;
import com.advanpro.putuan.model.KneelInfoSummary;
import com.advanpro.putuan.model.KneelInfoTrend;
import com.advanpro.putuan.service.KneelInfoService;
import com.advanpro.putuan.utils.common.Page;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/9/6
 * 描述： ${todo}.
 */
@Controller
public class KneelInfoController extends BaseController {

    @Autowired
    private KneelInfoService kneelInfoService;

    @ResponseBody
    @RequestMapping("/boss/kneel/list")
    public JsonResult queryKneelInfo(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginTime,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {
        try {
            List<KneelInfo> kneelInfoList = kneelInfoService.queryByTime(beginTime, endTime);
            return new JsonResult(true).addData("totalCount", kneelInfoList.size()).addData("result", kneelInfoList);
        } catch (Exception e) {
            logger.error("查询跪拜信息出错:", e);
            return new JsonResult(false, "查询跪拜信息出错");
        }

    }

    @ResponseBody
    @RequestMapping("/boss/kneel/get")
    public JsonResult queryKneelInfoByUserId(int userId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginTime,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {
        try {
            List<KneelInfo> kneelInfoList = kneelInfoService.queryByUserIdAndTime(userId, beginTime, endTime);
            return new JsonResult(true).addData("totalCount", kneelInfoList.size()).addData("result", kneelInfoList);
        } catch (Exception e) {
            logger.error("根据用户ID查询跪拜信息出错:", e);
            return new JsonResult(false, "根据用户ID查询跪拜信息出错");
        }

    }

    @ResponseBody
    @RequestMapping("/boss/kneel/summary")
    public JsonResult getKneelInfoSummary(String filter, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginTime,
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {
        try {
            List<KneelInfoSummary> kneelInfoList = kneelInfoService.getKneelInfoSummary(beginTime, endTime, filter);
            return new JsonResult(true).addData("totalCount", kneelInfoList.size()).addData("result", kneelInfoList);
        } catch (Exception e) {
            logger.error("查询用户跪拜统计信息出错:", e);
            return new JsonResult(false, "查询用户跪拜统计信息出错");
        }

    }

    @ResponseBody
    @RequestMapping("/boss/kneel/trend")
    public JsonResult getKneelInfoTrend(String filter, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginTime,
                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {
        try {
            List<KneelInfoTrend> kneelInfoTrendList = kneelInfoService.getKneelInfoTrend(beginTime, endTime, filter);
            return new JsonResult(true).addData("totalCount", kneelInfoTrendList.size()).addData("result", kneelInfoTrendList);
        } catch (Exception e) {
            logger.error("查询用户跪拜趋势走向出错:", e);
            return new JsonResult(false, "查询用户跪拜趋势走向出错");
        }

    }

    @ResponseBody
    @RequestMapping("/boss/kneel/detail")
    public JsonResult getKneelInfoDetail(UserKneelQuery userKneelQuery) {
        try {
            Page<KneelInfo> kneelInfo = kneelInfoService.getKneelInfoDetail(userKneelQuery);
            return new JsonResult(true).addData("totalCount", kneelInfo.getTotalCount()).addData("result", kneelInfo.getResult());
        } catch (Exception e) {
            logger.error("查询用户跪拜详细信息出错:", e);
            return new JsonResult(false, "查询用户跪拜详细信息出错");
        }

    }

    @RequestMapping(value = "/boss/kneel/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult add(KneelInfo kneelInfo) {
        try {
            kneelInfoService.add(kneelInfo);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("添加跪拜信息出错", e);
            return new JsonResult(false, "添加跪拜信息失败");
        }
    }

    @RequestMapping(value = "/boss/kneel/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult update(KneelInfo kneelInfo) {
        try {
            kneelInfoService.update(kneelInfo);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("更新跪拜信息出错", e);
            return new JsonResult(false, "更新设跪拜信息失败");
        }
    }
}
