package com.sr.controller;

import com.sr.common.HttpUtil;
import com.sr.common.ReturnCodeBuilder;
import com.sr.service.VipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/8 16:47
 */
@RequestMapping("/vip")
@Api(tags = {"会员管理"})
@RestController
public class VipController {
    @Autowired
    HttpUtil httpUtil;

    @Autowired
    VipService vipService;

    @ApiOperation(
            value = "开通会员",
            notes = "开通会员"
    )
    @RequestMapping(
            value = "/open",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> openVipAccount (@RequestParam(value = "month", required = true) Integer month,
                                               HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));
        Map<String, Object> map = vipService.openVipAccount(uid,month);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }


    @ApiOperation(
            value = "续费会员",
            notes = "续费会员"
    )
    @RequestMapping(
            value = "/renew",
            method = RequestMethod.PATCH
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> renewVipAccount (@RequestParam(value = "month", required = true) Integer month,
                                               HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));
        Map<String, Object> map = vipService.renewVipAccount(uid,month);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }


    @ApiOperation(
            value = "获取会员信息",
            notes = "获取会员信息"
    )
    @RequestMapping(
            value = "",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> getVipInfo (HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));
        Map<String, Object> map = vipService.getVipInfo(uid);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }

    @ApiOperation(
            value = "通过钱包开通会员",
            notes = "通过钱包开通会员"
    )
    @RequestMapping(
            value = "/open/wallet",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> openVipAccountByWallet (@RequestParam(value = "month", required = true) Integer month,
                                               HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));
        Map<String, Object> map = vipService.openVipAccountByWallet(uid,month);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }


    @ApiOperation(
            value = "通过钱包续费会员",
            notes = "通过钱包续费会员"
    )
    @RequestMapping(
            value = "/renew/wallet",
            method = RequestMethod.PATCH
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> renewVipAccountByWallet (@RequestParam(value = "month", required = true) Integer month,
                                                HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));
        Map<String, Object> map = vipService.renewVipAccountByWallet(uid,month);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }
}
