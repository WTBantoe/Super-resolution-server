package com.sr.controller;

import com.sr.common.HttpUtil;
import com.sr.common.ReturnCodeBuilder;
import com.sr.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/9 16:00
 */
@RequestMapping("/wallet")
@Api(tags = {"钱包管理"})
@RestController
public class WalletController {
    @Autowired
    HttpUtil httpUtil;

    @Autowired
    WalletService walletService;

    @ApiOperation(
            value = "首次开通",
            notes = "首次开通"
    )
    @RequestMapping(
            value = "",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> firstPost (HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));
        Map<String, Object> map = walletService.firstPost(uid);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }

    @ApiOperation(
            value = "充值",
            notes = "充值"
    )
    @RequestMapping(
            value = "/recharge",
            method = RequestMethod.PATCH
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> recharge (@RequestParam(value = "money", required = true) Long money,
                                                        HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));
        Map<String, Object> map = walletService.recharge(uid,money);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }

    @ApiOperation(
            value = "获取钱包余额",
            notes = "获取钱包余额"
    )
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> getWalletInfo (HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));
        Map<String, Object> map = walletService.getWalletInfo(uid);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }
}
