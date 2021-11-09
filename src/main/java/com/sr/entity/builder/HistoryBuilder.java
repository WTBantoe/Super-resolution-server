package com.sr.entity.builder;

import com.sr.entity.History;

import java.util.Date;

/**
 * @Author cyh
 * @Date 2021/11/4 19:03
 */
public final class HistoryBuilder
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

    private HistoryBuilder()
    {
    }

    public static HistoryBuilder aHistory()
    {
        return new HistoryBuilder();
    }

    public HistoryBuilder withId(Long id)
    {
        this.id = id;
        return this;
    }

    public HistoryBuilder withUid(Long uid)
    {
        this.uid = uid;
        return this;
    }

    public HistoryBuilder withRawMaterial(String rawMaterial)
    {
        this.rawMaterial = rawMaterial;
        return this;
    }

    public HistoryBuilder withResult(String result)
    {
        this.result = result;
        return this;
    }

    public HistoryBuilder withType(Integer type)
    {
        this.type = type;
        return this;
    }

    public HistoryBuilder withSpan(Long span)
    {
        this.span = span;
        return this;
    }

    public HistoryBuilder withTag(String tag)
    {
        this.tag = tag;
        return this;
    }

    public HistoryBuilder withGmtCreate(Date gmtCreate)
    {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public HistoryBuilder withGmtModify(Date gmtModify)
    {
        this.gmtModify = gmtModify;
        return this;
    }

    public History build()
    {
        History history = new History();
        history.setId(id);
        history.setUid(uid);
        history.setRawMaterial(rawMaterial);
        history.setResult(result);
        history.setType(type);
        history.setSpan(span);
        history.setTag(tag);
        history.setGmtCreate(gmtCreate);
        history.setGmtModify(gmtModify);
        return history;
    }
}
