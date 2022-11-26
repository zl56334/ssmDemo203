package com.example.ssmdemo203.module.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.example.ssmdemo203.common.Result;
import com.example.ssmdemo203.common.config.JwtProperties;
import com.example.ssmdemo203.common.utils.JwtTokenUtil;
import com.example.ssmdemo203.module.sys.pojo.entity.User;
import com.example.ssmdemo203.module.sys.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String bCPassword = bCryptPasswordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
//        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setPassword(bCPassword);
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

    @ResponseBody
    @RequestMapping("/updateuser")
    public Result updateUser(@RequestBody User user,HttpServletRequest httpServletRequest){
        Result result = new Result();
        String LoginUsername = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        User loginUser = userService.selectUserByUsername(LoginUsername);
        user.setUpdateUser(loginUser.getId());
        user.setUpdateTime(new Date());

        if (userService.updateUser(user) != 1){
            return result.code(10001).message("修改失败");
        }
        return result.code(10000).message("修改成功");
    }

    @ResponseBody
    @RequestMapping("/updateusers")
    public Result updateUsers(@RequestBody List<User> users, HttpServletRequest httpServletRequest){
        Result result = new Result();
        String LoginUsername = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        User loginUser = userService.selectUserByUsername(LoginUsername);

//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//            user.setUpdateUser(loginUser.getId());
//            user.setUpdateTime(new Date());
//        }

        List<User> saveList = new ArrayList<>();

        for (User user : users) {
            user.setUpdateUser(loginUser.getId());
            user.setUpdateTime(new Date());
            saveList.add(user);
        }

        Integer reInt = userService.updateUsers(saveList);
        return result.code(10000).message("已修改："+reInt+" 条数据");
    }

    @ResponseBody
    @RequestMapping("/updateuserbacth")
    public Result updateUserBacth(@RequestBody List<User> users, HttpServletRequest httpServletRequest){
        Result result = new Result();
        String LoginUsername = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        User loginUser = userService.selectUserByUsername(LoginUsername);

        List<User> saveList = new ArrayList<>();

        for (User user : users) {
            user.setUpdateUser(loginUser.getId());
            user.setUpdateTime(new Date());
            saveList.add(user);
        }

        Integer reInt = userService.updateUserBatch(saveList);
        return result.code(10000).message("已修改："+reInt+" 条数据");
    }

    @ResponseBody
    @RequestMapping("/deleteuser")
    public Result deleteUser(@RequestBody User user) {
        Integer reInt = userService.deleteUser(user);
        return new Result().code(10000).message("已删除："+reInt+" 条数据");
    }

    @ResponseBody
    @RequestMapping("/selectuserbystatus")
    public Result selectUserByStatus(@RequestBody User user) {

        if (user.getStatus() != null){
            List<User> reList = userService.selectUserByStatus(user);
            return new Result().code(10000).message("查询成功").data("userList",reList);
        }

        return new Result().code(10001).message("查询失败，缺少必要参数");
    }

}
