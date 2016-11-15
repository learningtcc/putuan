package com.advanpro.putuan.web.boss;

import com.advanpro.putuan.model.Device;
import com.advanpro.putuan.model.Product;
import com.advanpro.putuan.service.AccessTokenService;
import com.advanpro.putuan.service.DeviceService;
import com.advanpro.putuan.utils.common.Page;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.web.common.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/7/18
 * 描述： ${todo}.
 */
@Controller
public class DeviceController extends BaseController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AccessTokenService accessTokenService;

    @ResponseBody
    @RequestMapping("/boss/device/list")
    public JsonResult queryDevice(@RequestParam("keyword") String keyword, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        try {
            if (StringUtils.isEmpty( accessTokenService.getAccessToken())) {
                accessTokenService.updateAccessToken();
            }

            Page<Device> page = deviceService.queryByCondition(keyword, pageNo, pageSize);
            return new JsonResult(true).addData("totalCount", page.getTotalCount()).addData("result", page.getResult());
        } catch (Exception e) {
            logger.error("查询设备列表出错:", e);
            return new JsonResult(false, "查询设备列表出错");
        }

    }

    @RequestMapping(value = "/boss/device/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult add(Device device) {
        try {
            if (deviceService.queryByMac(device.getMac()) != null) {
                return new JsonResult(false, "设备MAC已存在");
            }
            deviceService.add(device);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("添加设备出错", e);
            return new JsonResult(false, "添加设备失败");
        }
    }

    @RequestMapping(value = "/boss/device/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult update(Device device) {
        try {
            deviceService.update(device);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("更新设备出错", e);
            return new JsonResult(false, "更新设备信息失败");
        }
    }

    @RequestMapping(value = "/boss/device/{id}")
    @ResponseBody
    public JsonResult queryById(@PathVariable("id") int id) {
        try {
            Device device = deviceService.queryById(id);
            return new JsonResult(true, "操作成功!").addData("device", device);
        } catch (Exception e) {
            logger.error("查询设备出错", e);
            return new JsonResult(false, "查询失败!");
        }
    }

}
