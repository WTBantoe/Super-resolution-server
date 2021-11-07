package com.sr.service;


import com.sr.entity.User;
import com.sr.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/9/28 10:47
 */
public interface UserService {
    String loginByTelephoneAndPasswordAndCheckPhoneNumber (String telephone, String password);

    String loginByTelephoneAndVerifyCode (String telephone, String verifyCode);

    String loginByThirdParty(String token);

    Map<String,Object> register (User user, String verifyCode);

    boolean logout(HttpServletRequest httpServletRequest);

    String loginByTelephoneAndPassword(String testTelephone, String testPassword);

    Map<String,Object> modifyUserInfo (UserInfo userInfo, Long uid);

    Map<String,Object> getUserInfo (Long uid);
}
