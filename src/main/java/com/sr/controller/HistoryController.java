package com.sr.controller;

import com.sr.common.HttpUtil;
import com.sr.common.ReturnCodeBuilder;
import com.sr.entity.History;
import com.sr.entity.User;
import com.sr.entity.builder.UserBuilder;
import com.sr.service.HistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/4 18:30
 */
@RequestMapping("/history")
@Api(tags = {"历史记录管理"})
@RestController
public class HistoryController {
    @Autowired
    HistoryService historyService;

    @Autowired
    HttpUtil httpUtil;

    @ApiOperation(
            value = "修改时间倒序获取用户历史记录",
            notes = "修改时间倒序获取用户历史记录"
    )
    @RequestMapping(
            value = "history/user/modify/desc",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> getUserHistoryListByModifyTimeDESC (@RequestParam(value = "page", required = true) Long page,
                                    @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                    @RequestParam(value = "tage", required = false) String tag,
                                    HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));
        List<Map<String, Object>> mapList = historyService.getUserHistoryListByModifyTimeDESC(uid,page,pageSize,tag);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(mapList)
                .buildMap();
    }

    @ApiOperation(
            value = "获取用户的历史数据标签",
            notes = "获取用户的历史数据标签"
    )
    @RequestMapping(
            value = "history/user/tags",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> getUserTags (HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));

        List<String> list = historyService.getUserTags(uid);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(list)
                .buildMap();
    }

}
