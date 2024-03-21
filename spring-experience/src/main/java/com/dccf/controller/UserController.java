package com.dccf.controller;

import com.dccf.spring.handwritten.anno.JController;
import com.dccf.spring.handwritten.anno.JRequestMapping;
import com.dccf.spring.handwritten.anno.JRequestParam;
import com.dccf.spring.handwritten.webmvc.servlet.JModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@JController
@JRequestMapping("/user")
public class UserController {

  @JRequestMapping("/getUserById")
  public JModelAndView getUserById(@JRequestParam("id") Long id) {
    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("uuid", UUID.randomUUID().toString());
    return new JModelAndView( "/detail.html",map);
  }
}
