package com.advanpro.putuan.web.device;

import com.advanpro.putuan.model.Device;
import com.advanpro.putuan.service.DeviceService;
import com.advanpro.putuan.utils.date.DateUtils;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.web.common.BaseController;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 作者： Joinly
 * 时间： 2016/8/31
 * 描述： 生成编号.
 */
@Controller
public class NumberController extends BaseController {

    @Autowired
    private DeviceService deviceService;

    @ResponseBody
    @RequestMapping("/device/number")
    public JsonResult randomNumber(String typeCode) {
        return new JsonResult(true).addData("number", getNumber(typeCode));
    }

    private String getNumber(String typeCode) {
        StringBuffer seqNumber = new StringBuffer(typeCode).append(DateUtils.formatDate(new Date(), DateUtils.DateFormatType.DATE_FORMAT_FOREX));
        synchronized (seqNumber) {
            seqNumber.append(RandomStringUtils.randomNumeric(5));
        }
        return seqNumber.toString();
    }

    /**
     * 把硬件注册到微信
     *
     * @return
     */
    @RequestMapping(value = "/device/register", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult register(String typeCode, String mac, String deviceNumber, String productId) {
        try {
            Device device = new Device();
            device.defaultDevice();
            if (deviceService.queryByMac(mac) != null) {
                return new JsonResult(false, "设备MAC已存在");
            }
            if (deviceService.queryByNo(deviceNumber) != null) {
                return new JsonResult(false, "设备编号已存在");
            }
            device.setMac(mac);
            device.setDeviceId(deviceNumber);
            device.setDeviceNumber(deviceNumber);
            device.setTypeCode(typeCode);
            device.setProductId(productId);
            String qrTicket = deviceService.add(device);
            return new JsonResult(true).addData("qrTicket", qrTicket);
        } catch (Exception e) {
            logger.error("注册设备出错", e);
            return new JsonResult(false, "注册设备失败");
        }
    }
}
