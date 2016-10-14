package com.advanpro.putuan.web.wechat;

import com.advanpro.putuan.model.Device;
import com.advanpro.putuan.model.KneelInfo;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.UserDevice;
import com.advanpro.putuan.service.DeviceService;
import com.advanpro.putuan.service.UserDeviceService;
import com.advanpro.putuan.service.UserService;
import com.advanpro.putuan.utils.date.DateUtils;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.utils.json.JsonUtils;
import com.advanpro.putuan.utils.json.StatusCode;
import com.advanpro.putuan.web.common.BaseController;
import com.advanpro.putuan.web.form.DeviceVo;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/9/28
 * 描述： ${todo}.
 */
@Controller
public class UserDeviceController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDeviceService userDeviceService;

    @Autowired
    private DeviceService deviceService;


    @ResponseBody
    @RequestMapping("/wx/device")
    public JsonResult getDevice(String openId) {
        try {
            User user = userService.getUserByOpenId(openId);
            List<UserDevice> userDeviceList = userDeviceService.getDevice(user.getId());
            List<DeviceVo> deviceVoList = Lists.newArrayList();
            if (userDeviceList != null && !userDeviceList.isEmpty()) {
                for (UserDevice userDevice : userDeviceList) {
                    DeviceVo deviceVo = new DeviceVo();
                    deviceVo.populateDevice(deviceService.queryByDeviceId(userDevice.getDeviceId()));
                    deviceVoList.add(deviceVo);
                }
            }

            return new JsonResult(true).addData("devices", deviceVoList);
        } catch (Exception e) {
            logger.error("获取用户设备信息出错:", e);
            return new JsonResult(false, "获取用户设备信息出错");
        }
    }

    /**
     * 解绑设备
     */
    @ResponseBody
    @RequestMapping(value = "/wx/device/unBind", method = RequestMethod.POST)
    public JsonResult unBindDevice(String openId, String deviceId) {
        try {
            userDeviceService.unBindDeviceWX(openId, deviceId);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("解绑设备出错：", e);
            return new JsonResult(false, "解绑设备出错");
        }
    }

}
