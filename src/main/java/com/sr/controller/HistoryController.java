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
import java.util.HashMap;
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
            value = "/modify/desc",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> getUserHistoryListByModifyTimeDESC (@RequestParam(value = "page", required = true) Long page,
                                    @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                    @RequestParam(value = "tag", required = false) String tag,
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
            value = "/tags",
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

    @ApiOperation(
            value = "获取用户的历史数据数量",
            notes = "获取用户的历史数据数量"
    )
    @RequestMapping(
            value = "count",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> getUserHistoryCount (HttpServletRequest httpServletRequest){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));
        long count = historyService.getCountByUid(uid);
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }


    @ApiOperation(
            value = "删除一条历史数据",
            notes = "删除一条历史数据"
    )
    @RequestMapping(
            value = "",
            method = RequestMethod.DELETE
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> deleteById (HttpServletRequest httpServletRequest,
                                           @RequestParam(required = true)Long historyId){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));
        boolean deleted = historyService.deleteById(historyId, uid);
        Map<String, Object> map = new HashMap<>();
        map.put("deleted", deleted ? "删除成功" : "删除失败");

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }

    @ApiOperation(
            value = "批量删除历史数据",
            notes = "批量删除历史数据"
    )
    @RequestMapping(
            value = "batch",
            method = RequestMethod.DELETE
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> deleteByIdList (HttpServletRequest httpServletRequest,
                                           @RequestParam(required = true)List<Long> historyIds){
        Long uid = httpUtil.getUidByToken(httpUtil.getToken(httpServletRequest));
        long count = historyService.deleteByIdList(historyIds, uid);
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }

}
