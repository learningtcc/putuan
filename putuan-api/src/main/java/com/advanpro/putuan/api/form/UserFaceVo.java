package com.advanpro.putuan.api.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * 作者： Joinly
 * 时间： 2015/11/5
 * 描述： todo.
 */
public class UserFaceVo {

    private int userId;

    private MultipartFile file;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
