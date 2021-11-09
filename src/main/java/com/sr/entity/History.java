package com.sr.entity;

import java.util.Date;

public class History
{
    private Long id;

    private Long uid;

    private String rawMaterial;

    private String result;

    private Integer type;

    private Long span;

    private String tag;

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

    public String getRawMaterial()
    {
        return rawMaterial;
    }

    public void setRawMaterial(String rawMaterial)
    {
        this.rawMaterial = rawMaterial == null ? null : rawMaterial.trim();
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result == null ? null : result.trim();
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Long getSpan()
    {
        return span;
    }

    public void setSpan(Long span)
    {
        this.span = span;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag == null ? null : tag.trim();
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