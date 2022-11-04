package com.example.ssmdemo203.module.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys")
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
