package com.advanpro.putuan.api.controller;

import com.advanpro.putuan.utils.json.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 作者： Joinly
 * 时间： 2016/8/22
 * 描述： ${todo}.
 */

@Controller
public class IndexController  {

    @RequestMapping("/")
    @ResponseBody
    public JsonResult index() {
        return new JsonResult(true, "蒲团api");
    }
}
