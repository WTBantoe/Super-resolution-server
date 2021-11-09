package com.sr.enunn;

/**
 * @Author cyh
 * @Date 2021/9/29 11:02
 */
public enum ThirdPartyLoginTypeEnum
{
    QQ(0, "QQ登录"), WX(1, "微信登录");

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

    ThirdPartyLoginTypeEnum(int code, String description)
    {
        this.code = code;
        this.description = description;
    }
}
