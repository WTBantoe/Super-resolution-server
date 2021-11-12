package com.sr.service;

import com.sr.entity.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/9 16:27
 */
public interface OrderService {
    Map<String, Object> createOrder(Order order);
    Map<String, Object> finishOrder(Order order);
    long orderCount(Long uid);
    long orderCountBetween(Long uid, Date startTime, Date endTime);
    List<Map<String, Object>> getOrderList(Long uid);
    List<Map<String, Object>> getOrderListPaging(Long uid, Long page, Integer pageSize);
    List<Map<String, Object>> getOrderListBetween(Long uid, Date startTime, Date endTime);
    List<Map<String, Object>> getOrderListBetweenPaging(Long uid, Date startTime, Date endTime, Long page, Integer pageSize);
    List<Map<String, Object>> getOrderUnfinished(Long uid);
    List<Map<String, Object>> abandonOrder(Long uid);
}
