package com.itheima.spring.service.impl;

import com.itheima.spring.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public void save() {
        System.out.println("this save method");
    }
}
