package com.sr.enunn;

/**
 * @Author cyh
 * @Date 2021/11/3 20:00
 */
public enum VipTypeEnum {
    COMMON(0,"普通用户"),
    VIP(1,"会员");

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    Integer code;
    String description;

    VipTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
