package com.advanpro.putuan.service.impl;

import com.advanpro.putuan.dao.AccountDao;
import com.advanpro.putuan.model.Account;
import com.advanpro.putuan.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者： Joinly
 * 时间： 2016/7/21
 * 描述： ${todo}.
 */

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public void add(Account account) {
        accountDao.add(account);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public void delete(int id) {
        accountDao.delete(id);
    }

    @Override
    public Account get(int id) {
        return accountDao.get(id);
    }

    @Override
    public Account getAccountByUserName(String userName) {
        return accountDao.getAccountByUserName(userName);
    }

    @Override
    public List<Account> list() {
        return accountDao.list();
    }
}
