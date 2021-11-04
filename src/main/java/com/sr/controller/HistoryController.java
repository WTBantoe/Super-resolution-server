package com.sr.controller;

import com.sr.common.ReturnCodeBuilder;
import com.sr.entity.History;
import com.sr.entity.User;
import com.sr.entity.builder.UserBuilder;
import com.sr.service.HistoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/4 18:30
 */
@RestController
public class HistoryController {
    @Autowired
    HistoryService historyService;
//    @ApiOperation(
//            value = "新增历史记录",
//            notes = "新增历史记录"
//    )
//    @RequestMapping(
//            value = "history",
//            method = RequestMethod.POST
//    )
//    @Transactional(
//            rollbackFor = Exception.class
//    )
//
//    public Map<String, Object> postHistory (@RequestParam(value = "telephone", required = true) String telephone,
//                                         @RequestParam(value = "password", required = true) String password,
//                                         @RequestParam(value = "userName", required = true) String userName,
//                                         @RequestParam(value = "verifyCode", required = true) String verifyCode){
//        History history =
//        User user = UserBuilder.anUser()
//                .withTelephone(telephone)
//                .withPassword(password)
//                .withUserName(userName)
//                .build();
//        Map<String, Object> map = historyService.post(history);
//        return ReturnCodeBuilder.successBuilder()
//                .addDataValue(map)
//                .buildMap();
//    }


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

    public Map<String, Object> get (@RequestParam(value = "uid", required = true) Long uid,
                                         @RequestParam(value = "page", required = true) Long page,
                                         @RequestParam(value = "pageSize", required = true) Integer pageSize){
        List<Map<String, Object>> mapList = historyService.getUserHistoryListByModifyTimeDESC(uid,page,pageSize);

        return ReturnCodeBuilder.successBuilder()
                .addDataValue(mapList)
                .buildMap();
    }
}
