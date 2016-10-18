package com.advanpro.putuan.service;

import com.advanpro.putuan.model.Version;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/10/18
 * 描述： ${todo}.
 */
public interface VersionService {

    void upload(Version version, MultipartFile file) throws IOException;

    void update(Version version);

    void delete(int id);

    Version get(int id);

    Version getNewest(String type);

    List<Version> query();
}
