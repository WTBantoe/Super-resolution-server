package com.sr.service.impl;

import com.sr.common.CollectionUtil;
import com.sr.common.EntityMapConvertor;
import com.sr.common.TimeUtil;
import com.sr.dao.VipMapper;
import com.sr.entity.Order;
import com.sr.entity.Vip;
import com.sr.entity.builder.OrderBuilder;
import com.sr.entity.example.VipExample;
import com.sr.enunn.*;
import com.sr.exception.StatusException;
import com.sr.service.OrderService;
import com.sr.service.VipService;
import com.sr.service.WalletService;
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

    @Autowired
    OrderService orderService;

    @Autowired
    WalletService walletService;

    public static Integer FREE_VIP_TIMES = 30;

    public static Integer VIP_FEE_PER_MONTH = 10;

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
        Vip vip = openVip(uid, mountCount);

        Order order = OrderBuilder.anOrder()
                .withOrderId(String.valueOf(System.currentTimeMillis()) + uid)
                .withUid(uid)
                .withMoney((long) (mountCount * VIP_FEE_PER_MONTH))
                .withMessage("开通会员")
                .withOrigin(OrderOriginEnum.ALIPAY.getCode())
                .withStatus(OrderStatusEnum.FINISHED.getCode())
                .withType(OrderOperationTypeEnum.VIP_RECHARGE.getCode())
                .build();
        orderService.createOrder(order);

        return EntityMapConvertor.entity2Map(vip);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> renewVipAccount(Long uid, Integer mountCount) {
        Vip vip = renewVip(uid, mountCount);

        Order order = OrderBuilder.anOrder()
                .withOrderId(String.valueOf(System.currentTimeMillis()) + uid)
                .withUid(uid)
                .withMoney((long) (mountCount * VIP_FEE_PER_MONTH))
                .withMessage("续费会员")
                .withOrigin(OrderOriginEnum.ALIPAY.getCode())
                .withStatus(OrderStatusEnum.FINISHED.getCode())
                .withType(OrderOperationTypeEnum.VIP_RECHARGE.getCode())
                .build();
        orderService.createOrder(order);

        return EntityMapConvertor.entity2Map(vip);
    }

    @Override
    public Map<String, Object> getVipInfo(Long uid) {
        Vip vip = getVipByUid(uid);
        return EntityMapConvertor.entity2Map(vip);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> renewVipAccountByWallet(Long uid, Integer mountCount) {
        walletService.consume(uid, (long) (mountCount * VIP_FEE_PER_MONTH), "续费会员", OrderOperationTypeEnum.VIP_RECHARGE);

        Vip vip = renewVip(uid, mountCount);

        return EntityMapConvertor.entity2Map(vip);
    }

    public Vip renewVip(Long uid, Integer mountCount) {
        VipExample vipExample = getExampleByUid(uid);
        Vip vip = CollectionUtil.getUniqueObjectFromList(vipMapper.selectByExample(vipExample));
        if (vip.getEndTime() == null || vip.getEndTime().getTime() < new Date().getTime()) {
            return openVip(uid, mountCount);
        } else {
            Date date = vip.getEndTime();
            vip.setEndTime(TimeUtil.addMonth(date,mountCount));
            vip.setLastRefreshTime(new Date());
            vipMapper.updateByExampleSelective(vip, vipExample);
            return vip;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> openVipAccountByWallet(Long uid, Integer mountCount) {
        walletService.consume(uid, (long) (mountCount * VIP_FEE_PER_MONTH), "开通会员", OrderOperationTypeEnum.VIP_RECHARGE);

        Vip vip = openVip(uid, mountCount);

        return EntityMapConvertor.entity2Map(vip);
    }

    public Vip openVip(Long uid, Integer mountCount) {
        VipExample vipExample = getExampleByUid(uid);
        Vip vip = CollectionUtil.getUniqueObjectFromList(vipMapper.selectByExample(vipExample));

        vip.setType(VipTypeEnum.VIP.getCode());
        Calendar calendar = Calendar.getInstance();
        vip.setStartTime(calendar.getTime());
        vip.setLastRefreshTime(calendar.getTime());
        vip.setEndTime(TimeUtil.addMonth(calendar, mountCount));
        vip.setFreeVipTimes(FREE_VIP_TIMES);
        vipMapper.updateByExampleSelective(vip, vipExample);

        return vip;
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
