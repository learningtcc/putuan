package com.advanpro.putuan.web.boss;

import com.advanpro.putuan.dao.query.UserQuery;
import com.advanpro.putuan.model.Product;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.Version;
import com.advanpro.putuan.service.VersionService;
import com.advanpro.putuan.utils.common.Page;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.utils.json.StatusCode;
import com.advanpro.putuan.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/10/18
 * 描述： ${todo}.
 */
@Controller
public class VersionController extends BaseController {

    @Autowired
    private VersionService versionService;

    @RequestMapping(value = "/boss/version/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult upload(Version version, MultipartFile file) {
        try {
            if (file == null) {
                return new JsonResult(false, "请选择文件!");
            }
            versionService.upload(version, file);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("上传版本出错", e);
            return new JsonResult(false, "上传版本失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/boss/version/update", method = RequestMethod.POST)
    public JsonResult update(Version version) {
        try {
            versionService.update(version);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("更新版本信息出错", e);
            return new JsonResult(false, "更新版本信息失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/boss/version/delete", method = RequestMethod.POST)
    public JsonResult delete(int id) {
        try {
            versionService.delete(id);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("删除版本信息出错", e);
            return new JsonResult(false, "删除版本信息失败");
        }
    }


    @RequestMapping(value = "/boss/version/{id}")
    @ResponseBody
    public JsonResult queryById(@PathVariable("id") int id) {
        try {
            Version version = versionService.get(id);
            return new JsonResult(true, "操作成功!").addData("version", version);
        } catch (Exception e) {
            logger.error("查询版本出错", e);
            return new JsonResult(false, "查询失败!");
        }
    }

    @ResponseBody
    @RequestMapping("/boss/version/list")
    public JsonResult query() {
        try {
            List<Version> versionList = versionService.query();
            return new JsonResult(true).addData("result", versionList);
        } catch (Exception e) {
            logger.error("查询版本列表出错:", e);
            return new JsonResult(false, "查询失败!");
        }

    }
}
