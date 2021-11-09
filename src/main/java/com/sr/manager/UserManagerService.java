package com.sr.manager;

import com.sr.entity.User;

import java.util.List;

/**
 * @Author cyh
 * @Date 2021/9/30 11:45
 */
public interface UserManagerService
{
    List<User> selectByUserWithUserNameLike(User user);

    List<User> selectByUserWithUserNameEqual(User user);

    List<User> selectByUserWithUserNameLikeAndCreateTimeAfter(User user);

    List<User> selectByUserWithUserNameLikeAndCreateTimeBefore(User user);
}
