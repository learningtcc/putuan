package com.advanpro.putuan.api.controller;

import com.advanpro.putuan.model.Version;
import com.advanpro.putuan.service.VersionService;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.utils.json.JsonUtils;
import com.advanpro.putuan.utils.json.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 作者： Vance
 * 时间： 2016/10/18
 * 描述： ${todo}.
 */
@Controller
public class VersionController extends BaseController {

    @Autowired
    private VersionService versionService;

    @ResponseBody
    @RequestMapping("/version/newest")
    public JsonResult getNewest(@RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }
            String type = (String) JsonUtils.toMap(param).get("type");
            Version version = versionService.getNewest(type);
            if (version == null) {
                return new JsonResult(StatusCode.OK);
            }
            return new JsonResult(StatusCode.OK).addData("version", version.getVersion()).addData("url", version.getUrl());
        } catch (Exception e) {
            logger.error("查询最新版本信息出错:", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }

    }
}
