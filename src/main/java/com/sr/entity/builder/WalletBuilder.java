package com.sr.entity.builder;

import com.sr.entity.Wallet;

import java.util.Date;

/**
 * @Author cyh
 * @Date 2021/11/9 16:14
 */
public final class WalletBuilder {
    private Long id;
    private Long uid;
    private Long balance;
    private Long totalIoutcome;
    private Long totalIncome;
    private String hash;
    private Date gmtCreate;
    private Date gmtModify;

    private WalletBuilder() {
    }

    public static WalletBuilder aWallet() {
        return new WalletBuilder();
    }

    public WalletBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public WalletBuilder withUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public WalletBuilder withBalance(Long balance) {
        this.balance = balance;
        return this;
    }

    public WalletBuilder withTotalIoutcome(Long totalIoutcome) {
        this.totalIoutcome = totalIoutcome;
        return this;
    }

    public WalletBuilder withTotalIncome(Long totalIncome) {
        this.totalIncome = totalIncome;
        return this;
    }

    public WalletBuilder withHash(String hash) {
        this.hash = hash;
        return this;
    }

    public WalletBuilder withGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public WalletBuilder withGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
        return this;
    }

    public Wallet build() {
        Wallet wallet = new Wallet();
        wallet.setId(id);
        wallet.setUid(uid);
        wallet.setBalance(balance);
        wallet.setTotalIoutcome(totalIoutcome);
        wallet.setTotalIncome(totalIncome);
        wallet.setHash(hash);
        wallet.setGmtCreate(gmtCreate);
        wallet.setGmtModify(gmtModify);
        return wallet;
    }
}
