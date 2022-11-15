package com.example.ssmdemo203.module.sys.controller;

import com.example.ssmdemo203.common.Result;
import com.example.ssmdemo203.common.config.JwtProperties;
import com.example.ssmdemo203.common.utils.JwtTokenUtil;
import com.example.ssmdemo203.module.sys.pojo.entity.User;
import com.example.ssmdemo203.module.sys.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;


    @RequestMapping("/registeruser")
    @ResponseBody
    public Result registerUser(HttpServletRequest httpServletRequest
            , @RequestParam(value = "username") String username
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "name") String name
            , @RequestParam(value = "avatar") String avatar
            , @RequestParam(value = "introduction") String introduction) {
        Result result = new Result();

        User checkUser = userService.selectUserByUsername(username);
        if (checkUser != null){
            return result.code(10001).message("用户已存在");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setAvatar(avatar);
        user.setIntroduction(introduction);

        String LoginUsername = jwtTokenUtil.getUsernameByRequest(httpServletRequest);

        User loginUser = userService.selectUserByUsername(LoginUsername);

        user.setCreateUser(loginUser.getId());
        user.setUpdateUser(loginUser.getId());

        user.setStatus(0);

        if (userService.registerUser(user) != 1) {
            return result.code(10001).message("注册失败");
        }

        return result.code(10000).message("注册成功");
    }

}
