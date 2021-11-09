package com.sr.enunn;

/**
 * @Author cyh
 * @Date 2021/11/9 22:24
 */
public enum OrderOriginEnum {
    WALLET(0,"钱包"),
    ALIPAY(1,"支付宝"),
    WX(2, "微信"),
    ;

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    Integer code;
    String description;

    OrderOriginEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
