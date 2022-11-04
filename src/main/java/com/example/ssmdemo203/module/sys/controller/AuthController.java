package com.example.ssmdemo203.module.sys.controller;

import com.example.ssmdemo203.common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/auth/")
public class AuthController {

    @RequestMapping("/login")
    public Result login(HttpServletRequest request){
        Result result = new Result();
        System.out.println(request.getParameter("username"));
        result.code(1).message("ok").data("X-Token","token123123");
        return result;
    }
}
