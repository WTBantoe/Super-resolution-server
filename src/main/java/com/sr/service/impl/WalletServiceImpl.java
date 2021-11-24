package com.sr.service.impl;

import com.sr.common.CollectionUtil;
import com.sr.common.EntityMapConvertor;
import com.sr.dao.WalletMapper;
import com.sr.entity.Order;
import com.sr.entity.Wallet;
import com.sr.entity.builder.OrderBuilder;
import com.sr.entity.builder.WalletBuilder;
import com.sr.entity.dto.WalletDTO;
import com.sr.entity.example.WalletExample;
import com.sr.enunn.OrderOperationTypeEnum;
import com.sr.enunn.OrderOriginEnum;
import com.sr.enunn.OrderStatusEnum;
import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import com.sr.service.OrderService;
import com.sr.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/9 15:53
 */
@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletMapper walletMapper;

    @Autowired
    OrderService orderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> post(Long uid, Long initMoney) {
        String hash = md5Wallet(uid, initMoney, initMoney, 0L);
        Wallet wallet = WalletBuilder.aWallet()
                .withUid(uid)
                .withBalance(initMoney)
                .withTotalIncome(initMoney)
                .withTotalIoutcome(0L)
                .withHash(hash)
                .build();

        Order order = OrderBuilder.anOrder()
                .withOrderId(String.valueOf(System.currentTimeMillis()) + uid)
                .withUid(uid)
                .withMoney(initMoney)
                .withMessage("钱包首次充值")
                .withOrigin(OrderOriginEnum.WALLET.getCode())
                .withStatus(OrderStatusEnum.FINISHED.getCode())
                .withType(OrderOperationTypeEnum.WALLET_RECHARGE.getCode())
                .build();
        orderService.createOrder(order);

        long id = walletMapper.insertSelective(wallet);
        wallet.setId(id);
        return EntityMapConvertor.entity2Map(WalletDTO.convertWalletToWalletDTO(wallet));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> recharge(Long uid, Long money) {
        if (CollectionUtils.isEmpty(walletMapper.selectByExample(getExampleByUid(uid)))) {
            return post(uid, money);
        }
        Wallet wallet = CollectionUtil.getUniqueObjectFromList(walletMapper.selectByExample(getExampleByUid(uid)));
        if (!verifyHash(wallet)) {
            throw new StatusException(StatusEnum.WALLET_HASH_INCORRECT);
        }
        wallet.setBalance(wallet.getBalance() + money);
        wallet.setTotalIncome(wallet.getTotalIncome() + money);
        wallet.setGmtModify(null);
        wallet.setGmtCreate(null);
        wallet.setHash(md5Wallet(wallet));
        walletMapper.updateByExample(wallet,getExampleByUid(uid));

        Order order = OrderBuilder.anOrder()
                .withOrderId(String.valueOf(System.currentTimeMillis()) + uid)
                .withUid(uid)
                .withMoney(money)
                .withMessage("钱包充值")
                .withOrigin(OrderOriginEnum.WALLET.getCode())
                .withStatus(OrderStatusEnum.FINISHED.getCode())
                .withType(OrderOperationTypeEnum.WALLET_RECHARGE.getCode())
                .build();
        orderService.createOrder(order);

        return EntityMapConvertor.entity2Map(WalletDTO.convertWalletToWalletDTO(wallet));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> consume(Long uid, Long money, String message, OrderOperationTypeEnum operationType) {
        Wallet wallet = CollectionUtil.getUniqueObjectFromList(walletMapper.selectByExample(getExampleByUid(uid)));
        if (!verifyHash(wallet)) {
            throw new StatusException(StatusEnum.WALLET_HASH_INCORRECT);
        }
        if (wallet.getBalance() < money) {
            throw new StatusException(StatusEnum.BALANCE_NOT_ENOUGH);
        }
        wallet.setBalance(wallet.getBalance() - money);
        wallet.setTotalIoutcome(wallet.getTotalIoutcome() + money);
        wallet.setGmtModify(null);
        wallet.setGmtCreate(null);
        wallet.setHash(md5Wallet(wallet));
        walletMapper.updateByExample(wallet,getExampleByUid(uid));

        Order order = OrderBuilder.anOrder()
                .withOrderId(String.valueOf(System.currentTimeMillis()) + uid)
                .withUid(uid)
                .withMoney(money)
                .withMessage(message)
                .withOrigin(OrderOriginEnum.WALLET.getCode())
                .withStatus(OrderStatusEnum.FINISHED.getCode())
                .withType(operationType.getCode())
                .build();
        orderService.createOrder(order);

        return EntityMapConvertor.entity2Map(WalletDTO.convertWalletToWalletDTO(wallet));
    }

    @Override
    public Map<String, Object> getWalletInfo(Long uid) {
        Wallet wallet = CollectionUtil.getUniqueObjectFromList(walletMapper.selectByExample(getExampleByUid(uid)));
        if (!verifyHash(wallet)) {
            throw new StatusException(StatusEnum.WALLET_HASH_INCORRECT);
        }
        return EntityMapConvertor.entity2Map(WalletDTO.convertWalletToWalletDTO(wallet));
    }

    private boolean verifyHash(Wallet wallet) {
        return wallet.getHash().equals(md5Wallet(wallet.getUid(),wallet.getBalance(),wallet.getTotalIncome(),wallet.getTotalIoutcome()));
    }

    private String md5Wallet(Wallet wallet){
        return md5Wallet(wallet.getUid(),wallet.getBalance(),wallet.getTotalIncome(),wallet.getTotalIoutcome());
    }

    private String md5Wallet(Long uid, Long balance, Long totalIncome, Long totalOutcome) {
        long key = uid + balance + totalIncome + totalOutcome;
        String string = Long.toString(key);
        if (string.isEmpty()) {
            return "";
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes("UTF-8"));
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private WalletExample getExampleByUid(Long uid) {
        WalletExample walletExample = new WalletExample();
        WalletExample.Criteria criteria = walletExample.createCriteria();
        criteria.andUidEqualTo(uid);
        return walletExample;
    }
}
