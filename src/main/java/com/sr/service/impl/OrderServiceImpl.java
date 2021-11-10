package com.sr.service.impl;

import com.sr.common.EntityMapConvertor;
import com.sr.dao.OrderMapper;
import com.sr.entity.Order;
import com.sr.entity.example.OrderExample;
import com.sr.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/9 16:27
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public Map<String, Object> createOrder(Order order) {
        long id = orderMapper.insertSelective(order);
        order.setId(id);
        return EntityMapConvertor.entity2Map(order);
    }

    @Override
    public Map<String, Object> finishOrder(Order order) {
        return null;
    }

    @Override
    public long orderCount(Long uid) {
        OrderExample orderExample = getOrderExampleByUid(uid);
        return orderMapper.countByExample(orderExample);
    }

    @Override
    public long orderCountBetween(Long uid, Date startTime, Date endTime) {
        OrderExample orderExample = getOrderExampleByUidAndBetweenTime(uid,startTime,endTime);
        return orderMapper.countByExample(orderExample);
    }

    @Override
    public List<Map<String, Object>> getOrderList(Long uid) {
        OrderExample orderExample = getOrderExampleByUid(uid);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        return EntityMapConvertor.entityList2MapList(orders);
    }

    @Override
    public List<Map<String, Object>> getOrderListPaging(Long uid, Long page, Integer pageSize) {
        OrderExample orderExample = addPaging(getOrderExampleByUid(uid),page,pageSize);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        return EntityMapConvertor.entityList2MapList(orders);
    }

    @Override
    public List<Map<String, Object>> getOrderListBetween(Long uid, Date startTime, Date endTime) {
        OrderExample orderExample = getOrderExampleByUidAndBetweenTime(uid,startTime,endTime);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        return EntityMapConvertor.entityList2MapList(orders);
    }

    @Override
    public List<Map<String, Object>> getOrderListBetweenPaging(Long uid, Date startTime, Date endTime, Long page, Integer pageSize) {
        OrderExample orderExample = addPaging(getOrderExampleByUidAndBetweenTime(uid,startTime,endTime),page,pageSize);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        return EntityMapConvertor.entityList2MapList(orders);
    }

    @Override
    public List<Map<String, Object>> getOrderUnfinished(Long uid) {
        return null;
    }

    @Override
    public List<Map<String, Object>> abandonOrder(Long uid) {
        return null;
    }

    private OrderExample getOrderExampleByUid(Long uid) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andUidEqualTo(uid);
        orderExample.setOrderByClause("gmt_create DESC");
        return orderExample;
    }

    private OrderExample getOrderExampleByUidAndBetweenTime(Long uid, Date startTime, Date endTime) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andGmtCreateBetween(startTime,endTime);
        orderExample.setOrderByClause("gmt_create DESC");
        return orderExample;
    }

    private OrderExample addPaging(OrderExample orderExample, Long page, Integer pageSize){
        if (page > 0) {
            Long offset = (page - 1L) * pageSize;
            orderExample.setLimit(pageSize);
            orderExample.setOffset(offset);
        }
        return orderExample;
    }
}
