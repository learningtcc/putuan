package com.advanpro.putuan.utils.json;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述： 封装返回的Json结果
 * 作者： Joinly
 * 时间： 2015/10/22.
 */
public class JsonResult {
    /**
     * 是否成功
     */
    private boolean success;
    private String msg;
    private StatusCode statusCode;
    private Map<String, Object> data = new HashMap<String, Object>();

    public JsonResult(boolean success) {
        this(success, StatusCode.OK);
    }

    public JsonResult(boolean success, String msg) {
        this(success, StatusCode.OK);
        this.msg = msg;
    }

    public JsonResult(boolean success, StatusCode statusCode) {
        this.success = success;
        this.statusCode = statusCode;
        this.msg = statusCode.getMessage();
    }

    public JsonResult(StatusCode statusCode) {
        this(statusCode == StatusCode.OK, statusCode);
    }

    public JsonResult data(String key, Object val) {
        data.put(key, val);
        return this;
    }

    public JsonResult data(Object val) {
        data.putAll(JsonUtils.toObject(JsonUtils.toJson(val), Map.class));
        return this;
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }

    public void toJson(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(toJson());
    }


    /**
     * 加入数据项
     *
     * @param name
     * @param value
     */
    public JsonResult addData(String name, Object value) {
        data.put(name, value);
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatusMsg() {
        return statusCode.getMessage();
    }

    public String getMsg() {
        return msg;
    }

    public JsonResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getStatusCode() {
        return statusCode.getValue();
    }

    public Map<String, Object> getData() {
        return data;
    }
}