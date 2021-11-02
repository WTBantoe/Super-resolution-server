package com.sr;

import com.sr.common.EntityMapConvertor;
import com.sr.entity.User;
import com.sr.entity.UserInfo;
import com.sr.entity.dto.UserRegisterDTO;
import org.testng.annotations.Test;

/**
 * @Author cyh
 * @Date 2021/11/2 16:13
 */
public class EntityMapConvertTest {
    @Test
    public void convertUserRegisterDTO2Map(){
        User user = new User();
        user.setUserName("cyh");
        user.setPassword("sdasdasfa");
        UserInfo userInfo = new UserInfo();
        userInfo.setSex(0);
        userInfo.setTrueName("cyh");
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(user,userInfo);
        System.out.println(EntityMapConvertor.entity2Map(userRegisterDTO));
    }
}
