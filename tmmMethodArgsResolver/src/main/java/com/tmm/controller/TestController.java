package com.tmm.controller;

import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String hello(@RequestAttribute("sid") Integer sid, @RequestAttribute("tid") Integer tid) {
        return "hello";
    }
}
