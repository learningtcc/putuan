package com.advanpro.putuan.model;

import java.io.Serializable;
import java.util.Date;


/**
 * 作者： Joinly
 * 时间： 2016/1/13
 * 描述： ${todo}.
*/

public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        if(id == null) {
            return 0;
        }
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
