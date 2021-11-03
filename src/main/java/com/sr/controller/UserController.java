package com.sr.controller;

import com.sr.common.ReturnCodeBuilder;
import com.sr.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/2 16:51
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation(
            value = "登录",
            notes = "登录"
    )
    @RequestMapping(
            value = "login",
            method = RequestMethod.GET,
            params = {"telephone", "password"}
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> LoginByTelephoneAndPassword (@RequestParam(value = "telephone", required = true) String telephone,
                         @RequestParam(value = "password", required = true) String password){
        String token = userService.LoginByTelephoneAndPassword(telephone,password);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }

    @ApiOperation(
            value = "注册",
            notes = "注册"
    )
    @RequestMapping(
            value = "registry",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> LoginByTelephoneAndPassword (@RequestParam(value = "telephone", required = true) String telephone,
                                                            @RequestParam(value = "password", required = true) String password,
                                                            @RequestParam(value = "userName", required = true) String userName,
                                                            @RequestParam(value = "verifyCode", required = true) String verifyCode){
        String token = userService.LoginByTelephoneAndPassword(telephone,password);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }
}
