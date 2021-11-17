package com.sr.aop;

import com.sr.common.HttpUtil;
import com.sr.manager.RedisManager;
import com.sr.service.UserService;
import com.sr.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author cyh
 * @Date 2021/9/28 10:15
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    RedisManager redisManager;

    @Autowired
    HttpUtil httpUtil;

    @Autowired
    UserService userService;

    @Value("${spring.profiles.active}")
    private String env;

    public static String TEST_TOKEN = "";

    public static String TEST_TELEPHONE = "11111111111";

    public static String TEST_PASSWORD = "111111";

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (env.equals("dev") || env.equals("test")){
//            if(StringUtils.isEmpty(httpUtil.getToken(request))){
//                if (!redisManager.hHasKey(UserServiceImpl.REDIS_TOKEN_KEY,TEST_TOKEN)){
//                    TEST_TOKEN = userService.loginByTelephoneAndPassword(TEST_TELEPHONE,TEST_PASSWORD);
//                }
//            }
//            if (!redisManager.hHasKey(UserServiceImpl.REDIS_TOKEN_KEY,httpUtil.getToken(request))){
//                TEST_TOKEN = userService.loginByTelephoneAndPassword(TEST_TELEPHONE,TEST_PASSWORD);
//            }
////            Cookie[] cookies = request.getCookies();
////            for (Cookie cookie : cookies){
////                if (cookie.getName().equals("token")){
////                    cookie.setValue(TEST_TOKEN);
////                    break;
////                }
////            }
//            AuthFilter.ModifyHttpServletRequestWrapper modifyHttpServletRequestWrapper = new AuthFilter.ModifyHttpServletRequestWrapper(request);
//            modifyHttpServletRequestWrapper.putCookie("token",TEST_TOKEN);
//            request = modifyHttpServletRequestWrapper;
            return true;
        }
        return redisManager.hHasKey(UserServiceImpl.REDIS_TOKEN_KEY, httpUtil.getToken(request));
    }
}
