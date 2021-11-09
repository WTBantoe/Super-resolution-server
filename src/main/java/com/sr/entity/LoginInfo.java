package com.sr.entity;

import java.util.Date;

public class LoginInfo
{
    private Long id;

    private Long uid;

    private Integer loginType;

    private Long loginCount;

    private Date lastLoginTime;

    private Date gmtCreate;

    private Date gmtModify;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getUid()
    {
        return uid;
    }

    public void setUid(Long uid)
    {
        this.uid = uid;
    }

    public Integer getLoginType()
    {
        return loginType;
    }

    public void setLoginType(Integer loginType)
    {
        this.loginType = loginType;
    }

    public Long getLoginCount()
    {
        return loginCount;
    }

    public void setLoginCount(Long loginCount)
    {
        this.loginCount = loginCount;
    }

    public Date getLastLoginTime()
    {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime)
    {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getGmtCreate()
    {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate)
    {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify()
    {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify)
    {
        this.gmtModify = gmtModify;
    }
}