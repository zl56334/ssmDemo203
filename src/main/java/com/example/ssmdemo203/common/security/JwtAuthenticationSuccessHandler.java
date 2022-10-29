package com.example.ssmdemo203.common.security;

import cn.hutool.json.JSONUtil;
import com.example.ssmdemo203.common.Result;
import com.example.ssmdemo203.common.ResultCode;
import com.example.ssmdemo203.common.utils.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义认证成功处理类
 */
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    //spring ioc 控制反转
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        final String realToken = jwtTokenUtil.generateToken(authentication.getName());

        Result result = new Result(ResultCode.SUCCESS,"登录成功");

        Map<String, Object> map = new HashMap<>();
        map.put("token",realToken);
        result.data(map);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String json = JSONUtil.toJsonStr(result);

        httpServletResponse.setContentType("text/json;charset=utf-8");

        httpServletResponse.getWriter().write(json);
    }
}
