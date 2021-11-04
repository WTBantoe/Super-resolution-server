package com.sr.service.impl;

import com.sr.dao.VipMapper;
import com.sr.entity.Vip;
import com.sr.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author cyh
 * @Date 2021/11/3 20:07
 */
@Service
public class VipServiceImpl implements VipService {
    @Autowired
    VipMapper vipMapper;

    @Override
    public int post(Vip vip) {
        return vipMapper.insertSelective(vip);
    }
}
