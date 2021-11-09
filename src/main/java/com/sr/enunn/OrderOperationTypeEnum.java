package com.sr.enunn;

/**
 * @Author cyh
 * @Date 2021/11/9 22:24
 */
public enum OrderOperationTypeEnum
{
    WALLET_RECHARGE(0, "钱包充值"), VIP_RECHARGE(1, "vip充值"), MATERIAL_PROCESS(2, "素材处理"), WALLET_WITHDRAW(3, "钱包提现"),
    ;

    public Integer getCode()
    {
        return code;
    }

    public String getDescription()
    {
        return description;
    }

    Integer code;
    String description;

    OrderOperationTypeEnum(int code, String description)
    {
        this.code = code;
        this.description = description;
    }
}
