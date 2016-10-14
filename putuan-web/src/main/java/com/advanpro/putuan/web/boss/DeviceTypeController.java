package com.advanpro.putuan.web.boss;

import com.advanpro.putuan.model.DeviceType;
import com.advanpro.putuan.service.DeviceTypeService;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/7/18
 * 描述： ${todo}.
 */
@Controller
public class DeviceTypeController extends BaseController {

    @Autowired
    private DeviceTypeService deviceTypeService;

    @ResponseBody
    @RequestMapping("/boss/devicetype/list")
    public JsonResult queryDevice() {
        try {
            List<DeviceType> deviceTypeList = deviceTypeService.queryAll();
            return new JsonResult(true).addData("totalCount", deviceTypeList.size()).addData("result", deviceTypeList);
        } catch (Exception e) {
            logger.error("查询设备类型列表出错:", e);
            return new JsonResult(false, "查询设备类型列表出错");
        }

    }

    @RequestMapping(value = "/boss/devicetype/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult add(DeviceType deviceType) {
        try {
            deviceTypeService.add(deviceType);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("添加设备类型出错", e);
            return new JsonResult(false, "添加设备类型失败");
        }
    }

    @RequestMapping(value = "/boss/devicetype/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult update(DeviceType deviceType) {
        try {
            deviceTypeService.update(deviceType);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("更新设备类型出错", e);
            return new JsonResult(false, "更新设备类型失败");
        }
    }

    @RequestMapping(value = "/boss/devicetype/{id}")
    @ResponseBody
    public JsonResult queryById(@PathVariable("id") int id) {
        try {
            DeviceType deviceType = deviceTypeService.queryById(id);
            return new JsonResult(true, "操作成功!").addData("deviceType", deviceType);
        } catch (Exception e) {
            logger.error("查询设备类型出错", e);
            return new JsonResult(false, "查询设备类型失败!");
        }
    }

}
