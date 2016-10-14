package com.advanpro.putuan.api.controller;

import com.advanpro.putuan.api.form.DeviceVo;
import com.advanpro.putuan.model.Device;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.UserDevice;
import com.advanpro.putuan.service.DeviceService;
import com.advanpro.putuan.service.UserDeviceService;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.utils.json.JsonUtils;
import com.advanpro.putuan.utils.json.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/9/27
 * 描述： ${todo}.
 */
@Controller
public class DeviceController extends BaseController {


    @Autowired
    private UserDeviceService userDeviceService;

    @Autowired
    private DeviceService deviceService;

    /**
     * 获取设备信息
     */
    @ResponseBody
    @RequestMapping(value = "/device/get", method = RequestMethod.POST)
    public JsonResult getDevice(HttpServletRequest request, @RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }

            String deviceId = (String) JsonUtils.toMap(param).get("deviceId");

            Device device = deviceService.queryByDeviceId(deviceId);
            DeviceVo deviceVo = new DeviceVo();
            if (device != null) {
                deviceVo.populateDevice(device);
            }
            return new JsonResult(StatusCode.OK).addData("device", deviceVo);
        } catch (Exception e) {
            logger.error("获取设备信息出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 绑定设备
     */
    @ResponseBody
    @RequestMapping(value = "/device/bind", method = RequestMethod.POST)
    public JsonResult bindDevice(HttpServletRequest request, @RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }

            int userId = Integer.valueOf(JsonUtils.toMap(param).get("userId").toString());
            String deviceId = (String) JsonUtils.toMap(param).get("deviceId");

            User user = getCurrentUser(request);
            if (user == null || user.getId() != userId)
                return new JsonResult(StatusCode.NOT_ALLOW);


            userDeviceService.bindDevice(userId, deviceId);
            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("绑定设备出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 解绑设备
     */
    @ResponseBody
    @RequestMapping(value = "/device/unBind", method = RequestMethod.POST)
    public JsonResult unBindDevice(HttpServletRequest request, @RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }

            int userId = Integer.valueOf(JsonUtils.toMap(param).get("userId").toString());
            String deviceId = (String) JsonUtils.toMap(param).get("deviceId");

            User user = getCurrentUser(request);
            if (user == null || user.getId() != userId)
                return new JsonResult(StatusCode.NOT_ALLOW);


            userDeviceService.unBindDevice(userId, deviceId);
            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("解绑设备出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

    /**
     * 检查设备是否绑定
     */
    @ResponseBody
    @RequestMapping(value = "/device/check", method = RequestMethod.POST)
    public JsonResult checkDevice(HttpServletRequest request, @RequestBody String param) {
        try {
            if (StringUtils.isEmpty(param)) {
                return new JsonResult(StatusCode.INVALID_PARAMETER);
            }

            int userId = Integer.valueOf(JsonUtils.toMap(param).get("userId").toString());
            String deviceId = (String) JsonUtils.toMap(param).get("deviceId");

            User user = getCurrentUser(request);
            if (user == null || user.getId() != userId)
                return new JsonResult(StatusCode.NOT_ALLOW);

            List<UserDevice> userDeviceList = userDeviceService.query(userId, deviceId);
            if (userDeviceList != null && !userDeviceList.isEmpty()) {
                return new JsonResult(StatusCode.DEVICE_BIND);
            }

            return new JsonResult(StatusCode.OK);
        } catch (Exception e) {
            logger.error("检查设备出错：", e);
            return new JsonResult(StatusCode.INTERNAL_ERROR);
        }
    }

}
