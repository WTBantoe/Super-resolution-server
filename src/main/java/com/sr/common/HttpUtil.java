package com.sr.common;

import com.sr.service.impl.UserServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author cyh
 * @Date 2021/11/4 19:14
 */
public class HttpUtil {
    public static String getToken(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        String token = "";
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(UserServiceImpl.REDIS_TOKEN_KEY)){
                token = cookie.getValue();
            }
        }
        return token;
    }
}
