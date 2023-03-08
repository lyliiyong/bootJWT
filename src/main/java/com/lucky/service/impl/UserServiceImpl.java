package com.lucky.service.impl;

import com.lucky.entity.User;
import com.lucky.mapper.UserMapper;
import com.lucky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author 不会笑的人是怪物
 * @Date 2023年 02月 08日 15:27
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user) {
        User userDB = userMapper.login(user);
        if (userDB != null) {
            return userDB;
        }
        throw new RuntimeException("登录失败~~");
    }
}
