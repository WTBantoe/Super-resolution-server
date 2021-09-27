package com.sr.entity;

import java.util.Date;

public class Vip {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getFreeTimes() {
        return freeTimes;
    }

    public void setFreeTimes(Integer freeTimes) {
        this.freeTimes = freeTimes;
    }

    public Integer getFreeVipTimes() {
        return freeVipTimes;
    }

    public void setFreeVipTimes(Integer freeVipTimes) {
        this.freeVipTimes = freeVipTimes;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getLastRefreshTime() {
        return lastRefreshTime;
    }

    public void setLastRefreshTime(Date lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}