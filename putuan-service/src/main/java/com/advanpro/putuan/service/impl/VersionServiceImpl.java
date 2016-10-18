package com.advanpro.putuan.service.impl;

import com.advanpro.putuan.dao.VersionDao;
import com.advanpro.putuan.model.Version;
import com.advanpro.putuan.service.VersionService;
import com.advanpro.putuan.utils.wx.MpProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
        fileName = version.getProduct() + version.getVersion() + suffix;

        // 上传
        String relativePath = uploadPath + "/" + version.getType() + "/" + version.getVersion();
        String url = domainUpload + "/" + version.getType() + "/" + version.getVersion() + "/" + fileName;
        File file = new File(relativePath + "/" + URLEncoder.encode(fileName, "UTF-8"));
        if (!file.getParentFile().exists()) {
            File folder = new File(relativePath);
            folder.mkdirs();
        }
        multipartFile.transferTo(file);
        Version newestVersion = getNewest(version.getType());
        if (newestVersion != null) {
            newestVersion.setCompany(version.getCompany());
            newestVersion.setDescription(version.getDescription());
            newestVersion.setProduct(version.getProduct());
            newestVersion.setVersion(version.getVersion());
            newestVersion.setUrl(url);
            versionDao.update(newestVersion);
        } else {
            versionDao.add(version);
        }
    }

    @Override
    public void update(Version version) {
        versionDao.update(version);
    }

    @Override
    public void delete(int id) {
        versionDao.delete(id);
    }

    @Override
    public Version get(int id) {
        return versionDao.get(id);
    }

    @Override
    public Version getNewest(String type) {
        return versionDao.getNewest(type);
    }

    @Override
    public List<Version> query() {
        return versionDao.query();
    }
}
