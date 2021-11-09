package com.sr.entity;

import java.util.Date;

public class AuthThirdParty
{
    private Long id;

    private Long uid;

    private Integer authType;

    private String loginToken;

    private Date expire;

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

    public Integer getAuthType()
    {
        return authType;
    }

    public void setAuthType(Integer authType)
    {
        this.authType = authType;
    }

    public String getLoginToken()
    {
        return loginToken;
    }

    public void setLoginToken(String loginToken)
    {
        this.loginToken = loginToken == null ? null : loginToken.trim();
    }

    public Date getExpire()
    {
        return expire;
    }

    public void setExpire(Date expire)
    {
        this.expire = expire;
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