package com.sr.enunn;

/**
 * @Author cyh
 * @Date 2021/9/30 15:54
 */
public enum StatusEnum {
    SUCCESS(200,"SUCCESS : 请求成功"),
    FAIL(700,"FAIL : 请求失败"),
    NO_AUTH(401,"NO_AUTH : 未经验证"),
    FORBIDDEN(403,"FORBIDDEN : 权限不足"),
    UNDEFINED_EXCEPTION(1000,"UNDEFINED_EXCEPTION : 未知错误"),
    INVALID_TELEPHONE_NUMBER(501,"INVALID_TELEPHONE_NUMBER : 非法手机号"),
    INVALID_VERIFY_CODE(502,"INVALID_VERIFY_CODE : 验证码错误"),
    USER_NOT_UNIQUE(503,"USER_NOT_UNIQUE : 数据库异常，用户不唯一！"),
    USER_NOT_EXIST(504,"USER_NOT_EXIST : 用户尚未注册!"),
    USER_ALREADY_EXIST(505,"USER_ALREADY_EXIST : 用户已注册!");

    int code;
    String description;

    public static StatusEnum getByCode (int code) {
        for (StatusEnum statusEnum : StatusEnum.class.getEnumConstants()) {
            if (code == statusEnum.getCode()) {
                return statusEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    StatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
