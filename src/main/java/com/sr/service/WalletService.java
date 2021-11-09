package com.sr.service;

import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/9 15:53
 */
public interface WalletService
{
    Map<String, Object> post(Long uid, Long initMoney);

    Map<String, Object> recharge(Long uid, Long money);

    Map<String, Object> consume(Long uid, Long money);

    Map<String, Object> getWalletInfo(Long uid);
}
