package com.sr.entity.dto;

import com.sr.entity.User;
import com.sr.entity.UserInfo;
import com.sr.entity.Vip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author cyh
 * @Date 2021/9/30 19:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    private User user;
    private Vip vip;

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "user=" + user.toString() +
                ", vip=" + vip.toString() +
                '}';
    }
}
