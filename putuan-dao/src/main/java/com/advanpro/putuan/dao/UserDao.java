package com.advanpro.putuan.dao;

import com.advanpro.putuan.dao.query.UserQuery;
import com.advanpro.putuan.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 作者： Joinly
 * 时间： 2016/7/7
 * 描述： ${todo}.
 */
public interface UserDao {

    User get(int id);

    User getUserByOpenId(String openId);

    List<User> queryUser();

    void add(User user);

    void update(User user);

    void updateById(User user);

    void delete(String opneId);

    List<User> queryUserByCondition(UserQuery userQuery);

    Integer countByCondition(UserQuery userQuery);

    List<User> queryByIds(List<Integer> idList);

    User getByAccount(String account);

    void updatePassword(@Param("id") int id, @Param("newPassword") String newPassword);

    void bindPhone(@Param("id") int id, @Param("phone") String phone);

    List<User> queryByAccount(String phone);

    List<User> queryByPhone(String phone);

    List<User> queryWX();
}
