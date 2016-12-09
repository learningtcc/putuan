package com.advanpro.putuan.service.impl;

import com.advanpro.putuan.dao.VersionDao;
import com.advanpro.putuan.model.Version;
import com.advanpro.putuan.service.VersionService;
import com.advanpro.putuan.utils.common.EncryptUtils;
import com.advanpro.putuan.utils.upload.UploadFile;
import com.advanpro.putuan.utils.wx.MpProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/10/18
 * 描述： ${todo}.
 */
@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private MpProperty mpProperty;

    @Autowired
    private VersionDao versionDao;

    @Override
    @Transactional
    public void upload(Version version, MultipartFile multipartFile) throws IOException {
        String uploadPath = mpProperty.getUploadPath();
        String domainUpload = mpProperty.getDomainUpload();

        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = version.getProduct() + "-" + version.getVersion() + suffix;
        if (StringUtils.isNotEmpty(version.getDeviceType())) {
            fileName = version.getProduct() + "-" + version.getDeviceType() + "-" + version.getVersion() + suffix;
        }

        // 上传
        String path = "/" + version.getType() + "/" + version.getVersion();
        String relativePath = uploadPath + path;
        String url = domainUpload + path + "/" + fileName;
        version.setMd5(EncryptUtils.getFileMD5String(multipartFile.getInputStream()));
        version.setUrl(url);
        UploadFile.transferTo(multipartFile, relativePath, URLEncoder.encode(fileName, "UTF-8"));
        versionDao.add(version);
    }

    @Override
    public void update(Version version) {
        versionDao.update(version);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Version version = versionDao.get(id);
        String uploadPath = mpProperty.getUploadPath();
        String domainUpload = mpProperty.getDomainUpload();
        String path = uploadPath + version.getUrl().replace(domainUpload, "");
        UploadFile.deleteFile(path);
        versionDao.delete(id);
    }

    @Override
    public Version get(int id) {
        return versionDao.get(id);
    }

    @Override
    public Version getNewest(String type, String deviceType) {
        return versionDao.getNewest(type, deviceType);
    }

    @Override
    public List<Version> query() {
        return versionDao.query();
    }
}
