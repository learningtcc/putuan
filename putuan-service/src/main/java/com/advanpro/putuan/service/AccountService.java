package com.advanpro.putuan.service;

import com.advanpro.putuan.model.Account;

import java.util.List;

/**
 * 作者： Joinly
 * 时间： 2016/7/21
 * 描述： ${todo}.
 */
public interface AccountService {

    void add(Account account);

    void update(Account account);

    void delete(int id);

    Account get(int id);

    Account getAccountByUserName(String userName);

    List<Account> list();
}
