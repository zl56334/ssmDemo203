package com.example.ssmdemo203.module.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/sys")
public class LoginController {

    @GetMapping("/login")
    public ModelAndView login(Model model){
        model.addAttribute("name","test_name");
        model.addAttribute("time",new Date());
        return new ModelAndView("page/login.html");
    }

}
