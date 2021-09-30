package com.sr.service.impl;

import com.sr.dao.UserInfoMapper;
import com.sr.dao.UserMapper;
import com.sr.entity.User;
import com.sr.entity.UserInfo;
import com.sr.entity.builder.UserBuilder;
import com.sr.enunn.StatusEnum;
import com.sr.exception.StatusException;
import com.sr.manager.RedisManager;
import com.sr.manager.UserManagerService;
import com.sr.service.UserService;
import com.sr.util.TelephoneCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author cyh
 * @Date 2021/9/28 14:05
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserManagerService userManagerService;

    @Autowired
    RedisManager redisManager;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public String Login (String telephone, String password) {
        //TODO 第三方登录
        if (!TelephoneCheck.checkTelephoneNumber(telephone)) {
            throw new StatusException(StatusEnum.INVALID_TELEPHONE_NUMBER);
        }

        User user = UserBuilder
                .anUser()
                .withTelephone(telephone)
                .withPassword(password)
                .build();

        List<User> users = userManagerService.selectByUserWithUserNameEqual(user);
        if (users == null) {
            throw new StatusException(StatusEnum.USER_NOT_EXIST);
        }
        if (users.size() > 1) {
            throw new StatusException(StatusEnum.USER_NOT_UNIQUE);
        }
        String token = UUID.randomUUID().toString().replaceAll("-","");
        redisManager.set(token,users.get(0).getId());
        return token;
    }

    @Override
    @Transactional
    public Map<String, Object> register (User user, UserInfo userInfo, String verifyCode) {
        if (!TelephoneCheck.checkTelephoneNumber(user.getTelephone())) {
            throw new StatusException(StatusEnum.INVALID_TELEPHONE_NUMBER);
        }
        if (!TelephoneCheck.checkTelephoneNumberAndCode(user.getTelephone(),verifyCode)) {
            throw new StatusException(StatusEnum.INVALID_VERIFY_CODE);
        }
        int uid = userMapper.insertSelective(user);
        int userInfoId = userInfoMapper.insertSelective(userInfo);
        //TODO 缺少buildmap
        return null;
    }


    public void LoginByThirdParty (String token) {

    }

}
