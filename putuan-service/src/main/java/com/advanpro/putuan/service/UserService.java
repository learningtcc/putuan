package com.advanpro.putuan.service;

import com.advanpro.putuan.dao.query.UserQuery;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.utils.common.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * 作者： Joinly
 * 时间： 2016/7/7
 * 描述： ${todo}.
 */
public interface UserService {

    User get(int id);

    User getUserByOpenId(String openId);

    User getBindUserByOpenId(String openId);

    User getByAccount(String account);

    void add(User user);

    void update(User user);

    void updateById(User user);

    void delete(String openId);

    Page<User> queryUserByCondition(UserQuery userQuery);

    void bindUser(User wxUser, User appUser);

    void unBindUser(User wxUser, User appUser);

    void register(User user, String kneelCount, String deviceId);

    void updatePassword(int userId, String newPassword);

    void bindPhone(int userId, String phone);

    void uploadFace(int id, MultipartFile multipartFile) throws IOException;

    List<User> queryByAccount(String phone);

    List<User> queryByPhone(String phone);

    List<User> queryWX();

    int getUserCount();
}
