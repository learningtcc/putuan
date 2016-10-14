package com.advanpro.putuan.web.boss;

import com.advanpro.putuan.model.Product;
import com.advanpro.putuan.service.ProductService;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/7/21
 * 描述： ${todo}.
 */
@Controller
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @ResponseBody
    @RequestMapping("/boss/product/list")
    public JsonResult queryDevice() {
        try {

            List<Product> productList = productService.queryAll();
            return new JsonResult(true).addData("totalCount", productList.size()).addData("result", productList);
        } catch (Exception e) {
            logger.error("查询产品列表出错:", e);
            return new JsonResult(false, "查询产品列表出错");
        }

    }

    @RequestMapping(value = "/boss/product/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult add(Product product) {
        try {
            productService.add(product);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("添加产品出错", e);
            return new JsonResult(false, "添加产品失败");
        }
    }

    @RequestMapping(value = "/boss/product/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult update(Product product) {
        try {
            productService.update(product);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("更改产品出错", e);
            return new JsonResult(false, "更改产品失败");
        }
    }

    @RequestMapping(value = "/boss/product/{id}")
    @ResponseBody
    public JsonResult queryById(@PathVariable("id") int id) {
        try {
            Product product = productService.queryById(id);
            return new JsonResult(true, "操作成功!").addData("product", product);
        } catch (Exception e) {
            logger.error("查询产品出错", e);
            return new JsonResult(false, "查询失败!");
        }
    }
}
