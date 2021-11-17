package com.sr.entity.builder;

import com.sr.entity.Order;

import java.util.Date;

/**
 * @Author cyh
 * @Date 2021/11/9 22:14
 */
public final class OrderBuilder {
    private Long id;
    private Long uid;
    private String orderId;
    private Integer origin;
    private Integer type;
    private String message;
    private Long money;
    private Integer status;
    private Date gmtCreate;
    private Date gmtModify;

    private OrderBuilder() {
    }

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder withUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public OrderBuilder withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderBuilder withOrigin(Integer origin) {
        this.origin = origin;
        return this;
    }

    public OrderBuilder withType(Integer type) {
        this.type = type;
        return this;
    }

    public OrderBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public OrderBuilder withMoney(Long money) {
        this.money = money;
        return this;
    }

    public OrderBuilder withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public OrderBuilder withGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public OrderBuilder withGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setId(id);
        order.setUid(uid);
        order.setOrderId(orderId);
        order.setOrigin(origin);
        order.setType(type);
        order.setMessage(message);
        order.setMoney(money);
        order.setStatus(status);
        order.setGmtCreate(gmtCreate);
        order.setGmtModify(gmtModify);
        return order;
    }
}
