package com.sr.controller;

import com.sr.common.HttpUtil;
import com.sr.common.ReturnCodeBuilder;
import com.sr.entity.User;
import com.sr.entity.UserInfo;
import com.sr.entity.builder.UserBuilder;
import com.sr.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/11/2 16:51
 */
@RequestMapping("/user")
@Api(tags = {"用户管理"})
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    HttpUtil httpUtil;

    @ApiOperation(
            value = "手机密码登录",
            notes = "手机密码登录"
    )
    @RequestMapping(
            value = "login/password",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> LoginByTelephoneAndPassword (@RequestParam(value = "telephone", required = true) String telephone,
                         @RequestParam(value = "password", required = true) String password){
        String token = userService.loginByTelephoneAndPasswordAndCheckPhoneNumber(telephone,password);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }

    @ApiOperation(
            value = "手机验证码登录",
            notes = "手机验证码登录"
    )
    @RequestMapping(
            value = "login/code",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )

    public Map<String, Object> LoginByTelephoneAndCode (@RequestParam(value = "telephone", required = true) String telephone,
                                                            @RequestParam(value = "code", required = true) String code){
        String token = userService.loginByTelephoneAndVerifyCode(telephone,code);
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

    public Map<String, Object> register (@RequestParam(value = "telephone", required = true) String telephone,
                                                            @RequestParam(value = "password", required = true) String password,
                                                            @RequestParam(value = "userName", required = true) String userName,
                                                            @RequestParam(value = "verifyCode", required = true) String verifyCode){
        User user = UserBuilder.anUser()
                .withTelephone(telephone)
                .withPassword(password)
                .withUserName(userName)
                .build();
        Map<String, Object> map = userService.register(user,verifyCode);
        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }

    @ApiOperation(
            value = "退出登录",
            notes = "退出登录"
    )
    @RequestMapping(
            value = "logout",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    public Map<String, Object> Logout(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("successful", userService.logout(request) ? "登出成功" : "登出失败");
        return ReturnCodeBuilder.successBuilder()
                .addDataValue(map)
                .buildMap();
    }

    @ApiOperation(
            value = "获得头像",
            notes = "获得头像"
    )
    @RequestMapping(
            value = "avatar",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    public String getAvatar(HttpServletRequest request) {
        return userService.getAvatar(httpUtil.getUidByToken(httpUtil.getToken(request)));
    }


    @ApiOperation(
            value = "修改个人信息",
            notes = "修改个人信息"
    )
    @RequestMapping(
            value = "info",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    public Map<String, Object> modifyUserInfo(@RequestBody UserInfo userInfo,
                                              HttpServletRequest request) {
        return userService.modifyUserInfo(userInfo,httpUtil.getUidByToken(httpUtil.getToken(request)));
    }


    @ApiOperation(
            value = "获得个人信息",
            notes = "获得个人信息"
    )
    @RequestMapping(
            value = "info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        return userService.getUserInfo(httpUtil.getUidByToken(httpUtil.getToken(request)));
    }
}
