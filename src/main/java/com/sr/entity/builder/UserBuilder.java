package com.sr.entity.builder;

import com.sr.entity.User;

import java.util.Date;

/**
 * @Author cyh
 * @Date 2021/9/30 12:09
 */
public final class UserBuilder {
    private Long id;
    private String userName;
    private String password;
    private String telephone;
    private Integer type;
    private Integer status;
    private Date gmtCreate;
    private Date gmtModify;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public UserBuilder withType(Integer type) {
        this.type = type;
        return this;
    }

    public UserBuilder withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public UserBuilder withGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public UserBuilder withGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setUserName(userName);
        user.setPassword(password);
        user.setTelephone(telephone);
        user.setType(type);
        user.setStatus(status);
        user.setGmtCreate(gmtCreate);
        user.setGmtModify(gmtModify);
        return user;
    }
}
