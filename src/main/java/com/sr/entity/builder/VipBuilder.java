package com.sr.entity.builder;

import com.sr.entity.Vip;

import java.util.Date;

/**
 * @Author cyh
 * @Date 2021/11/3 20:03
 */
public final class VipBuilder
{
    private Long id;
    private Long uid;
    private Integer freeTimes;
    private Integer freeVipTimes;
    private Date startTime;
    private Date endTime;
    private Integer type;
    private Date lastRefreshTime;
    private Date gmtCreate;
    private Date gmtModify;

    private VipBuilder()
    {
    }

    public static VipBuilder aVip()
    {
        return new VipBuilder();
    }

    public VipBuilder withId(Long id)
    {
        this.id = id;
        return this;
    }

    public VipBuilder withUid(Long uid)
    {
        this.uid = uid;
        return this;
    }

    public VipBuilder withFreeTimes(Integer freeTimes)
    {
        this.freeTimes = freeTimes;
        return this;
    }

    public VipBuilder withFreeVipTimes(Integer freeVipTimes)
    {
        this.freeVipTimes = freeVipTimes;
        return this;
    }

    public VipBuilder withStartTime(Date startTime)
    {
        this.startTime = startTime;
        return this;
    }

    public VipBuilder withEndTime(Date endTime)
    {
        this.endTime = endTime;
        return this;
    }

    public VipBuilder withType(Integer type)
    {
        this.type = type;
        return this;
    }

    public VipBuilder withLastRefreshTime(Date lastRefreshTime)
    {
        this.lastRefreshTime = lastRefreshTime;
        return this;
    }

    public VipBuilder withGmtCreate(Date gmtCreate)
    {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public VipBuilder withGmtModify(Date gmtModify)
    {
        this.gmtModify = gmtModify;
        return this;
    }

    public Vip build()
    {
        Vip vip = new Vip();
        vip.setId(id);
        vip.setUid(uid);
        vip.setFreeTimes(freeTimes);
        vip.setFreeVipTimes(freeVipTimes);
        vip.setStartTime(startTime);
        vip.setEndTime(endTime);
        vip.setType(type);
        vip.setLastRefreshTime(lastRefreshTime);
        vip.setGmtCreate(gmtCreate);
        vip.setGmtModify(gmtModify);
        return vip;
    }
}
