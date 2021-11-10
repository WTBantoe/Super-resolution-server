package com.sr.enunn;

/**
 * @Author cyh
 * @Date 2021/11/3 17:06
 */
public enum UserTypeEnum {
    USER(0,"普通用户"),
    ADMIN(1,"后台人员");

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    Integer code;
    String description;

    UserTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
