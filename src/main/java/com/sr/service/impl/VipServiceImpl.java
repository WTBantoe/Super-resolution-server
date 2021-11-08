package com.sr.service.impl;

import com.sr.common.CollectionUtil;
import com.sr.common.EntityMapConvertor;
import com.sr.common.TimeUtil;
import com.sr.dao.VipMapper;
import com.sr.entity.Vip;
import com.sr.entity.example.VipExample;
import com.sr.enunn.StatusEnum;
import com.sr.enunn.VipTypeEnum;
import com.sr.exception.StatusException;
import com.sr.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
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

    public static Integer FREE_VIP_TIMES = 30;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> openVipAccount(Long uid, Integer mountCount) {
        VipExample vipExample = getExampleByUid(uid);
        Vip vip = CollectionUtil.getUniqueObjectFromList(vipMapper.selectByExample(vipExample));

        vip.setType(VipTypeEnum.VIP.getCode());
        Calendar calendar = Calendar.getInstance();
        vip.setStartTime(calendar.getTime());
        vip.setLastRefreshTime(calendar.getTime());
        vip.setEndTime(TimeUtil.addMonth(calendar,mountCount));
        vip.setFreeVipTimes(FREE_VIP_TIMES);

        vipMapper.updateByExampleSelective(vip, vipExample);

        return EntityMapConvertor.entity2Map(vip);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> renewVipAccount(Long uid, Integer mountCount) {
        VipExample vipExample = getExampleByUid(uid);
        Vip vip = CollectionUtil.getUniqueObjectFromList(vipMapper.selectByExample(vipExample));
        Date date = vip.getEndTime();
        vip.setEndTime(TimeUtil.addMonth(date,mountCount));
        vip.setLastRefreshTime(new Date());

        vipMapper.updateByExampleSelective(vip, vipExample);

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
