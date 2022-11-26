package com.example.ssmdemo203.module.sys.service.impl;

import com.example.ssmdemo203.module.sys.dao.UserDao;
import com.example.ssmdemo203.module.sys.pojo.entity.User;
import com.example.ssmdemo203.module.sys.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
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

    @Override
    public Integer updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public Integer updateUsers(List<User> users) {
        int reInt = 0;
        for (User user : users) {
            reInt = reInt + userDao.updateUser(user);
        }
        return reInt;
    }

    @Override
    public Integer updateUserBatch(List<User> users) {
        int reInt = 0;
        for (User user : users) {
            reInt = reInt + userDao.updateUser(user);
        }
        return reInt;
    }

    @Override
    public Integer deleteUser(User user) {
        return userDao.deleteUser(user);
    }

    @Override
    public List<User> selectUserByStatus(User user) {
        return userDao.selectUserByStatus(user);
    }

}
