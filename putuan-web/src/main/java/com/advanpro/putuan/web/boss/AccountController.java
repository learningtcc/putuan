package com.advanpro.putuan.web.boss;

import com.advanpro.putuan.model.Account;
import com.advanpro.putuan.service.AccountService;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/9/22
 * 描述： ${todo}.
 */
@Controller
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    @ResponseBody
    @RequestMapping("/boss/account/list")
    public JsonResult queryAccount() {
        try {
            List<Account> accountList = accountService.list();

            return new JsonResult(true).addData("totalCount", accountList.size()).addData("result", accountList);
        } catch (Exception e) {
            logger.error("查询管理员列表出错:", e);
            return new JsonResult(false, "查询管理员列表出错");
        }

    }

    @RequestMapping(value = "/boss/account/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult add(Account account) {
        try {
            accountService.add(account);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("添加管理员出错", e);
            return new JsonResult(false, "添加管理员失败");
        }
    }

    @RequestMapping(value = "/boss/account/delete", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult delete(int id) {
        try {
            accountService.delete(id);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("删除管理员信息出错", e);
            return new JsonResult(false, "删除管理员信息失败");
        }
    }

    @RequestMapping(value = "/boss/account/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult update(Account account) {
        try {
            accountService.update(account);
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("更新管理员出错", e);
            return new JsonResult(false, "更新管理员失败");
        }
    }

    @RequestMapping(value = "/boss/account/{id}")
    @ResponseBody
    public JsonResult queryById(@PathVariable("id") int id) {
        try {
            Account account = accountService.get(id);
            return new JsonResult(true, "操作成功!").addData("account", account);
        } catch (Exception e) {
            logger.error("查询管理员出错", e);
            return new JsonResult(false, "查询失败!");
        }
    }
}
