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
        if (users == null) {
            throw new StatusException(StatusEnum.USER_NOT_EXIST);
        }
        if (users.size() > 1) {
            throw new StatusException(StatusEnum.USER_NOT_UNIQUE);
        }
        Long uid = users.get(0).getId();
        return SetUserToken(uid);
    }

    @Override
    public String LoginByTelephoneAndVerifyCode(String telephone, String verifyCode) {
        return null;
    }

    public String LoginByThirdParty (String token) {
        return null;
    }

    private String SetUserToken(Long uid) {
        String token = UUID.randomUUID().toString().replaceAll("-","");
        redisManager.set(token, uid,100000);
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
            throw new StatusException(StatusEnum.USER_REGISTER_FAIL);
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
}
