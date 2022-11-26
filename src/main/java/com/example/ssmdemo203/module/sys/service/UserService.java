package com.example.ssmdemo203.module.sys.service;

import com.example.ssmdemo203.module.sys.pojo.entity.User;

import java.util.List;

public interface UserService {

    Integer registerUser(User user);

    User selectUserByUsername(String username);

    Integer updateUser(User user);

    Integer updateUsers(List<User> users);

    Integer updateUserBatch(List<User> users);

    Integer deleteUser(User user);

    List<User> selectUserByStatus(User user);

}
