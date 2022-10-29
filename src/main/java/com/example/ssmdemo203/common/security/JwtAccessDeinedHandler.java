package com.example.ssmdemo203.common.security;

import cn.hutool.json.JSONUtil;
import com.example.ssmdemo203.common.Result;
import com.example.ssmdemo203.common.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义无权访问处理类
 */
@Component
public class JwtAccessDeinedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        System.out.println("权限不足");

        Result result = new Result();

        result.code(ResultCode.UNAUTHRIZED).message("权限不足");

        httpServletResponse.setContentType("text/json;charset=utf-8");

        httpServletResponse.getWriter().write(JSONUtil.toJsonStr(result));
    }
}
