package com.example.ssmdemo203.module.sys.service;

import com.example.ssmdemo203.module.sys.pojo.entity.User;

public interface UserService {

    Integer registerUser(User user);

    User selectUserByUsername(String username);

}
