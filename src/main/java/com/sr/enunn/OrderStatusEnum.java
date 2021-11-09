package com.sr.enunn;

/**
 * @Author cyh
 * @Date 2021/11/9 22:25
 */
public enum OrderStatusEnum
{
    CREATED(0, "订单创建"), FINISHED(1, "订单完成"), ABANDON(2, "订单废弃"),
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

    OrderStatusEnum(int code, String description)
    {
        this.code = code;
        this.description = description;
    }
}
