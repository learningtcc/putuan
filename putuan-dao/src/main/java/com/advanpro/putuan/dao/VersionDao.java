package com.advanpro.putuan.dao;

import com.advanpro.putuan.model.Version;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/10/18
 * 描述： ${todo}.
 */
public interface VersionDao {

    void add(Version version);

    void update(Version version);

    void delete(int id);

    Version get(int id);

    /**
     * 获取最新版本
     *
     * @param type
     */
    Version getNewest(@Param("type") String type, @Param("deviceType") String deviceType);

    List<Version> query();
}
