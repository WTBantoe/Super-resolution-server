package com.sr.service.impl;

import com.sr.common.CollectionUtil;
import com.sr.common.EntityMapConvertor;
import com.sr.dao.VipMapper;
import com.sr.entity.Vip;
import com.sr.entity.example.VipExample;
import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import com.sr.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> useFreeTime(Long uid) {
        Vip vip = getVipByUid(uid);
        if (vip.getFreeVipTimes() != null && vip.getFreeVipTimes() > 0) {
            vip.setFreeVipTimes(vip.getFreeVipTimes() - 1);
        } else {
            if (vip.getFreeTimes() != null && vip.getFreeTimes() > 0) {
                vip.setFreeTimes(vip.getFreeTimes() - 1);
            } else {
                throw new StatusException(StatusEnum.FREE_TIME_NOT_ENOUGH);
            }
        }
        VipExample vipExample = getExampleByUid(uid);
        vipMapper.updateByExampleSelective(vip,vipExample);
        return EntityMapConvertor.entity2Map(vip);
    }

    public VipExample getExampleByUid (Long uid) {
        VipExample vipExample = new VipExample();
        VipExample.Criteria criteria = vipExample.createCriteria();
        criteria.andUidEqualTo(uid);
        return vipExample;
    }

    public List<Vip> getVipListByUid(Long uid) {
        return vipMapper.selectByExample(getExampleByUid(uid));
    }

    public Vip getVipByUid(Long uid) {
        return CollectionUtil.getUniqueObjectFromList(getVipListByUid(uid));
    }
}
