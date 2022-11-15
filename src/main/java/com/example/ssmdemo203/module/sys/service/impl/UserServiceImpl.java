package com.example.ssmdemo203.module.sys.service.impl;

import com.example.ssmdemo203.module.sys.dao.UserDao;
import com.example.ssmdemo203.module.sys.pojo.entity.User;
import com.example.ssmdemo203.module.sys.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Integer registerUser(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userDao.insertUser(user);
    }

    @Override
    public User selectUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

}
