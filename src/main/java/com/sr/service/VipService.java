package com.sr.service;

import com.sr.entity.Vip;

import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/3 20:05
 */
public interface VipService {
    public int post(Vip vip);

    Map<String,Object> useFreeTime (Long uid);

    Map<String,Object> openVipAccount(Long uid, Integer mountCount);

    Map<String,Object> renewVipAccount(Long uid, Integer mountCount);
}
