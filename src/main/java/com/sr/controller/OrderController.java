package com.sr.controller;

import com.sr.common.HttpUtil;
import com.sr.common.ReturnCodeBuilder;
import com.sr.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/9 17:14
 */
@RequestMapping("/order")
@Api(tags = {"订单管理"})
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    HttpUtil httpUtil;

    @ApiOperation(
            value = "获取全部订单信息",
            notes = "获取全部订单信息"
    )
    @RequestMapping(
            value = "all",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> getAllOrderList (HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(orderService.getOrderList(uid))
                .addDataCount(orderService.orderCount(uid))
                .buildMap();
    }

    @ApiOperation(
            value = "分页获取全部订单信息",
            notes = "分页获取全部订单信息"
    )
    @RequestMapping(
            value = "all/page",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> getAllOrderListPaging (@RequestParam(value = "pageIndex", required = true) Long pageIndex,
                                                      @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                                      HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(orderService.getOrderListPaging(uid,pageIndex,pageSize))
                .addDataCount(orderService.orderCount(uid))
                .buildMap();
    }

    @ApiOperation(
            value = "获取一段时间内的订单信息",
            notes = "获取一段时间内的订单信息"
    )
    @RequestMapping(
            value = "all/between",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> getAllOrderListBetween (@RequestParam(value = "startTime", required = true) Date startTime,
                                                       @RequestParam(value = "endTime", required = true) Date endTime,
                                                       HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(orderService.getOrderListBetween(uid,startTime,endTime))
                .addDataCount(orderService.orderCountBetween(uid,startTime,endTime))
                .buildMap();
    }

    @ApiOperation(
            value = "分页获取一段时间内的订单信息",
            notes = "分页获取一段时间内的订单信息"
    )
    @RequestMapping(
            value = "all/between/page",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> getAllOrderListBetween (@RequestParam(value = "startTime", required = true) Date startTime,
                                                       @RequestParam(value = "endTime", required = true) Date endTime,
                                                       @RequestParam(value = "pageIndex", required = true) Long pageIndex,
                                                       @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                                       HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(orderService.getOrderListBetweenPaging(uid,startTime,endTime,pageIndex,pageSize))
                .addDataCount(orderService.orderCountBetween(uid,startTime,endTime))
                .buildMap();
    }
}
