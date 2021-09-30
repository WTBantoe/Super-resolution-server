package com.sr.service;


import com.sr.entity.User;
import com.sr.entity.UserInfo;

import java.util.Map;

/**
 * @Author cyh
 * @Date 2021/9/28 10:47
 */
public interface UserService {
    String Login (String telephone, String password);

    Map<String,Object> register (User user, UserInfo userInfo, String verifyCode);
}
