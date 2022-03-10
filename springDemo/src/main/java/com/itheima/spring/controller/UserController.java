package com.itheima.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/user")
public class UserController {
    @RequestMapping("/save")
    public String save(){
        System.out.println("save");
        return "save.jsp";
    }

}
