package com.sr.manager.impl;

import com.sr.dao.UserMapper;
import com.sr.entity.User;
import com.sr.entity.example.UserExample;
import com.sr.manager.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author cyh
 * @Date 2021/9/30 11:46
 */
@Service
public class UserManagerServiceImpl implements UserManagerService {
    @Autowired
    UserMapper userMapper;

    /**
     * 查找用户，其中用户名是模糊查询
     * @param user
     * @return
     */
    @Override
    public List<User> selectByUserWithUserNameLike(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        buildUserCriteria(user,criteria);
        if (user.getUserName() != null) {
            criteria.andUserNameLike(user.getUserName());
        }
        return userMapper.selectByExample(userExample);
    }

    /**
     * 查找用户，其中用户名是精准查询
     * @param user
     * @return
     */
    @Override
    public List<User> selectByUserWithUserNameEqual(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        buildUserCriteria(user,criteria);
        if (user.getUserName() != null) {
            criteria.andUserNameEqualTo(user.getUserName());
        }
        return userMapper.selectByExample(userExample);
    }

    /**
     * 查找创建晚于某个时间点的用户，其中用户名模糊查询
     * @param user
     * @return
     */
    @Override
    public List<User> selectByUserWithUserNameLikeAndCreateTimeAfter(User user) {
        if (user.getGmtCreate() == null) {
            return new ArrayList<>();
        }
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        buildUserCriteria(user,criteria);
        if (user.getUserName() != null) {
            criteria.andUserNameLike(user.getUserName());
        }
        criteria.andGmtCreateGreaterThanOrEqualTo(user.getGmtCreate());
        return userMapper.selectByExample(userExample);
    }

    /**
     * 查找创建早于某个时间点的用户，其中用户名模糊查询
     * @param user
     * @return
     */
    @Override
    public List<User> selectByUserWithUserNameLikeAndCreateTimeBefore(User user) {
        if (user.getGmtCreate() == null) {
            return new ArrayList<>();
        }
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        buildUserCriteria(user,criteria);
        if (user.getUserName() != null) {
            criteria.andUserNameLike(user.getUserName());
        }
        criteria.andGmtCreateLessThanOrEqualTo(user.getGmtCreate());
        return userMapper.selectByExample(userExample);
    }


    public void buildUserCriteria(User user, UserExample.Criteria criteria){
        if (user.getId() != null) {
            criteria.andIdEqualTo(user.getId());
        }
        if (user.getTelephone() != null) {
            criteria.andTelephoneEqualTo(user.getTelephone());
        }
        if (user.getPassword() != null) {
            criteria.andPasswordEqualTo(user.getPassword());
        }
        if (user.getType() != null) {
            criteria.andTypeEqualTo(user.getType());
        }
        if (user.getStatus() != null) {
            criteria.andStatusEqualTo(user.getStatus());
        }
    }
}
