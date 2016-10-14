package com.advanpro.putuan.service.impl;

import com.advanpro.putuan.dao.KneelInfoDao;
import com.advanpro.putuan.dao.UserDao;
import com.advanpro.putuan.dao.UserDeviceDao;
import com.advanpro.putuan.dao.query.UserQuery;
import com.advanpro.putuan.model.KneelInfo;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.UserDevice;
import com.advanpro.putuan.service.UserService;
import com.advanpro.putuan.utils.common.Page;
import com.advanpro.putuan.utils.date.DateUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 作者： Vance
 * 时间： 2016/9/20
 * 描述： ${todo}.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDeviceDao userDeviceDao;

    @Autowired
    private KneelInfoDao kneelInfoDao;

    @Override
    public User get(int id) {
        User user = userDao.get(id);
        setBindNickName(user);
        return user;
    }

    @Override
    public User getUserByOpenId(String openId) {
        User user = userDao.getUserByOpenId(openId);
        return user;
    }

    @Override
    public User getBindUserByOpenId(String openId) {
        User user = userDao.getUserByOpenId(openId);
        User userApp = userDao.get(user.getUserId());
        if (userApp != null) {
            user = userApp;
        }
        return user;
    }

    @Override
    public User getByAccount(String account) {
        return userDao.getByAccount(account);
    }

    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void updateById(User user) {
        userDao.updateById(user);
    }

    @Override
    public void delete(String openId) {
        userDao.delete(openId);
    }

    @Override
    public Page<User> queryUserByCondition(UserQuery userQuery) {
        Page<User> page = new Page<>(userQuery.getPageNo(), userQuery.getPageSize());
        userQuery.setStart(page.getStart());
        userQuery.setLimit(userQuery.getPageSize());
        List<User> userList = userDao.queryUserByCondition(userQuery);
        for (User user : userList) {
            setBindNickName(user);
        }
        page.setResult(userList);
        page.setTotalCount(userDao.countByCondition(userQuery));
        return page;
    }

    /**
     * 绑定用户
     *
     * @param wxUser  微信 User
     * @param appUser APP User
     */
    @Override
    @Transactional
    public void bindUser(User wxUser, User appUser) {
        if (wxUser.getId() == 0) {
            userDao.add(wxUser);
        }
        wxUser.setUserId(appUser.getId());
        wxUser.setPhone(appUser.getAccount());
        appUser.setUserId(wxUser.getId());
        userDao.updateById(wxUser);
        userDao.updateById(appUser);
    }

    /**
     * 解绑用户
     *
     * @param wxUser  微信 User
     * @param appUser APP User
     */
    @Override
    @Transactional
    public void unBindUser(User wxUser, User appUser) {
        wxUser.setUserId(0);
        appUser.setUserId(0);
        userDao.updateById(wxUser);
        userDao.updateById(appUser);
    }


    /**
     * APP用户注册
     *
     * @param user
     * @param kneelCount
     * @param deviceId
     */
    @Override
    @Transactional
    public void register(User user, String kneelCount, String deviceId) {
        userDao.add(user);
        if (StringUtils.isNotEmpty(kneelCount)) {
            KneelInfo kneelInfo = new KneelInfo();
            kneelInfo.setUserId(user.getId());
            kneelInfo.setKneelCount(Integer.valueOf(kneelCount));
            kneelInfo.setStartTime(DateUtils.addDays(DateUtils.getCurrentDate(), -1));
            kneelInfoDao.add(kneelInfo);
        }
        if (StringUtils.isNotEmpty(deviceId)) {
            UserDevice userDevice = new UserDevice();
            userDevice.setUserId(user.getId());
            userDevice.setDeviceId(deviceId);
            userDevice.setDeviceUsing(1);
            userDeviceDao.add(userDevice);
        }

        //注册时查找绑定手机的微信并关联起来
        List<User> userList = userDao.queryByPhone(user.getAccount());
        for (User wxUser : userList) {
            if ("WX".equals(wxUser.getUserType())) {
                bindUser(wxUser, user);
            }
        }

    }

    @Override
    public void updatePassword(int id, String newPassword) {
        userDao.updatePassword(id, newPassword);
    }

    @Override
    public void bindPhone(int id, String phone) {
        userDao.bindPhone(id, phone);
    }

    @Override
    public void uploadFace(int id, MultipartFile multipartFile) throws IOException {
        final String uploadPath = "/data/upload/putuan";
        final String uploadUrl = "http://api.putuan.net/putuan";
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;  // 用UUID重命名头像

        // 上传
        String relativePath = uploadPath + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String url = uploadUrl + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + fileName;
        File file = new File(relativePath + "/" + URLEncoder.encode(fileName, "UTF-8"));
        if (!file.getParentFile().exists()) {
            File folder = new File(relativePath);
            folder.mkdirs();
        }
        //保存到一个目标文件中。
        multipartFile.transferTo(file);
        User user = userDao.get(id);
        user.setHeadimgUrl(url);
        userDao.updateById(user);
    }

    @Override
    public List<User> queryByAccount(String phone) {
        return userDao.queryByAccount(phone);
    }

    @Override
    public List<User> queryByPhone(String phone) {
        return userDao.queryByPhone(phone);
    }

    @Override
    public List<User> queryWX() {
        return userDao.queryWX();
    }

    @Override
    public int getUserCount() {
        UserQuery userQuery = new UserQuery();
        return userDao.countByCondition(userQuery);
    }

    private void setBindNickName(User user) {
        if (user == null) {
            return;
        }
        User bindUser = userDao.get(user.getUserId());
        if (bindUser == null) {
            return;
        }
        user.setBindNickName(bindUser.getNickName());
    }


    /**
     * 过滤绑定的微信用户
     *
     * @param userMap
     * @return
     */
    public Map<Integer, User> filterBindWxUser(Map<Integer, User> userMap) {
        Set<Integer> filterIds = Sets.newHashSet();
        for (User user : userMap.values()) {
            if ("APP".equalsIgnoreCase(user.getUserType())) {
                filterIds.add(user.getUserId());
            }
        }

        Map<Integer, User> filterMap = Maps.newHashMap(userMap);
        for (int id : filterIds) {
            if (filterMap.containsKey(id)) {
                filterMap.remove(id);
            }
        }

        return filterMap;
    }
}
