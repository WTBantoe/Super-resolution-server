package com.sr.entity.builder;

import com.sr.entity.UserInfo;

import java.util.Date;

/**
 * @Author cyh
 * @Date 2021/11/7 17:10
 */
public final class UserInfoBuilder {
    private Long id;
    private Long uid;
    private Integer sex;
    private String avatar;
    private String idCardNumber;
    private String trueName;
    private Date gmtCreate;
    private Date gmtModify;

    private UserInfoBuilder() {
    }

    public static UserInfoBuilder anUserInfo() {
        return new UserInfoBuilder();
    }

    public UserInfoBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserInfoBuilder withUid(Long uid) {
        this.uid = uid;
        return this;
    }

    public UserInfoBuilder withSex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public UserInfoBuilder withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public UserInfoBuilder withIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
        return this;
    }

    public UserInfoBuilder withTrueName(String trueName) {
        this.trueName = trueName;
        return this;
    }

    public UserInfoBuilder withGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public UserInfoBuilder withGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
        return this;
    }

    public UserInfo build() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setUid(uid);
        userInfo.setSex(sex);
        userInfo.setAvatar(avatar);
        userInfo.setIdCardNumber(idCardNumber);
        userInfo.setTrueName(trueName);
        userInfo.setGmtCreate(gmtCreate);
        userInfo.setGmtModify(gmtModify);
        return userInfo;
    }
}
