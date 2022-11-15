package com.example.ssmdemo203.module.sys.controller;


import cn.hutool.core.util.StrUtil;
import com.example.ssmdemo203.common.Result;
import com.example.ssmdemo203.common.security.impl.UserDetailsServiceImpl;
import com.example.ssmdemo203.common.utils.JwtTokenUtil;
import com.example.ssmdemo203.module.sys.pojo.entity.User;
import jdk.internal.dynalink.support.NameCodec;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/auth/")
public class AuthController {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/login")
    //添加返回json格式数据 到 Response body中
    @ResponseBody
    public Result login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){
        //创建返回类对象
        Result result = new Result();
        //获取 parm 数据

        if (StrUtil.isNotEmpty(username) && StrUtil.isNotEmpty(password)){

            //获取数据库对应用户
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (userDetails != null){
                try {
                    //security框架验证用户
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username,password);
                    //添加对应request 到通行证
                    Authentication authentication = authenticationManager.authenticate(authenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    //生成加密过的token令牌
                    String token = jwtTokenUtil.generateToken(username);

                    return result.code(10000).message("登录成功").data("X-Token",token);
                } catch (Exception e){
                    e.printStackTrace();
                    return result.code(10001).message("密码错误");
                }
            }
            return result.code(10001).message("无效用户名");
        }
        return result.code(10001).message("用户名或密码为空");
    }

    @RequestMapping("/getUserInfoByToken")
    @ResponseBody
    public Result getUserInfoByToken(@RequestParam("token") String token){
        Result result = new Result();
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return result.code(10000).message("查询成功").data("userInfo",userDetails);
    }

}
