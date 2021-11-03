package com.sr.service.impl;

import com.sr.common.EntityMapConvertor;
import com.sr.dao.UserInfoMapper;
import com.sr.dao.UserMapper;
import com.sr.entity.User;
import com.sr.entity.Vip;
import com.sr.entity.builder.UserBuilder;
import com.sr.entity.builder.VipBuilder;
import com.sr.entity.dto.UserRegisterDTO;
import com.sr.enunn.StatusEnum;
import com.sr.enunn.UserStatusEnum;
import com.sr.enunn.UserTypeEnum;
import com.sr.enunn.VipTypeEnum;
import com.sr.exception.StatusException;
import com.sr.manager.RedisManager;
import com.sr.manager.UserManagerService;
import com.sr.service.UserService;
import com.sr.common.TelephoneCheck;
import com.sr.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author cyh
 * @Date 2021/9/28 14:05
 */
@Service
public class UserServiceImpl implements UserService {

    public static int USER_FREE_TIMES = 10;

    public static String REDIS_TOKEN_KEY = "token";

    public static String REDIS_USER_KEY = "user";

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserManagerService userManagerService;

    @Autowired
    RedisManager redisManager;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    VipService vipService;

    @Override
    public String LoginByTelephoneAndPassword (String telephone, String password) {
        if (!TelephoneCheck.checkTelephoneNumber(telephone)) {
            throw new StatusException(StatusEnum.INVALID_TELEPHONE_NUMBER);
        }

        User user = UserBuilder
                .anUser()
                .withTelephone(telephone)
                .withPassword(password)
                .build();

        List<User> users = userManagerService.selectByUserWithUserNameLike(user);
        if (CollectionUtils.isEmpty(users)) {
            throw new StatusException(StatusEnum.USER_NOT_EXIST);
        }
        if (users.size() > 1) {
            throw new StatusException(StatusEnum.USER_NOT_UNIQUE);
        }
        return SetUserToken(users.get(0));
    }

    @Override
    public String LoginByTelephoneAndVerifyCode(String telephone, String verifyCode) {
        if (!TelephoneCheck.checkTelephoneNumber(telephone)) {
            throw new StatusException(StatusEnum.INVALID_TELEPHONE_NUMBER);
        }
        User user = UserBuilder.anUser()
                .withTelephone(telephone)
                .build();
        List<User> users = userManagerService.selectByUserWithUserNameLike(user);
        if (CollectionUtils.isEmpty(users)) {
            throw new StatusException(StatusEnum.USER_NOT_EXIST);
        }
        if (users.size() > 1) {
            throw new StatusException(StatusEnum.USER_NOT_UNIQUE);
        }
        if (!TelephoneCheck.checkTelephoneNumberAndCode(user.getTelephone(),verifyCode)) {
            throw new StatusException(StatusEnum.INVALID_VERIFY_CODE);
        }
        return SetUserToken(users.get(0));
    }

    public String LoginByThirdParty (String token) {
        return null;
    }

    private String SetUserToken(User user) {
        String token = UUID.randomUUID().toString().replaceAll("-","");
        redisManager.hSet(REDIS_TOKEN_KEY, token, user.getId(), 100000);
        redisManager.hSet(REDIS_USER_KEY, user.getId().toString(), user, 100000);
        return token;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> register (User user, String verifyCode) {
        if (!TelephoneCheck.checkTelephoneNumber(user.getTelephone())) {
            throw new StatusException(StatusEnum.INVALID_TELEPHONE_NUMBER);
        }
        if (!TelephoneCheck.checkTelephoneNumberAndCode(user.getTelephone(),verifyCode)) {
            throw new StatusException(StatusEnum.INVALID_VERIFY_CODE);
        }
        int uid;
        user.setType(UserTypeEnum.USER.getCode());
        user.setStatus(UserStatusEnum.AVAILABLE.getCode());
        try {
            uid = userMapper.insertSelective(user);
        }catch (Exception e){
            throw new StatusException(StatusEnum.USER_REGISTER_FAILED);
        }
        user.setId((long)uid);

        Vip vip = VipBuilder.aVip()
                .withFreeVipTimes(0)
                .withFreeTimes(USER_FREE_TIMES)
                .withUid((long)uid)
                .withType(VipTypeEnum.COMMON.getCode())
                .build();

        int vipId;
        try {
            vipId = vipService.post(vip);
        }catch (Exception e) {
            throw new StatusException(StatusEnum.USER_REGISTER_FAIL_WITH_VIP);
        }

        vip.setId((long)vipId);

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(user,vip);

        return EntityMapConvertor.entity2Map(userRegisterDTO);
    }

    @Override
    public boolean logout(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies){
                if(cookie.getName().equals(REDIS_TOKEN_KEY)){
                    String userKey = (String) redisManager.hGet(REDIS_TOKEN_KEY, cookie.getValue());
                    redisManager.hDel(REDIS_USER_KEY, userKey);
                    redisManager.hDel(REDIS_TOKEN_KEY, cookie.getValue());
                    return true;
                }
            }
        }
        return false;
    }
}
