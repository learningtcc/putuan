package com.advanpro.putuan.utils.upload;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者： Joinly
 * 时间： 2015/12/2
 * 描述： todo.
 */
public class UploadFile {

    private static final Log log = LogFactory.getLog(UploadFile.class);

    private static Map<String, String> contentTypeMap = new HashMap<>();

    static {
        contentTypeMap.put(".jpg", "image/jpg");
        contentTypeMap.put(".gif", "image/gif");
        contentTypeMap.put(".png", "image/png");
        contentTypeMap.put(".jpeg", "image/jpeg");
        contentTypeMap.put(".bmp", "application/x-bmp");
    }

    /**
     * 检查是否是图片
     *
     * @param name
     * @return
     */
    public static boolean isPicture(String name) {
        name = name.toLowerCase();
        for (String type : contentTypeMap.keySet()) {
            if (name.toLowerCase().endsWith(type))
                return true;
        }
        return false;
    }

    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String getImageContentType(String name) {
        String fileType = getFileType(name).toLowerCase();
        return contentTypeMap.get(fileType);
    }


    /**
     * 上传文件
     *
     * @param multipartFile
     * @param uploadPath    上传的路径
     * @return
     * @throws IOException
     */
    public static File transferTo(MultipartFile multipartFile, String uploadPath, String fileName) throws IOException {
        File file = new File(uploadPath + "/" + fileName);
        if (!file.getParentFile().exists()) {
            File folder = new File(uploadPath);
            folder.mkdirs();
        }
        //保存到一个目标文件中。
        multipartFile.transferTo(file);
        return file;
    }

}
