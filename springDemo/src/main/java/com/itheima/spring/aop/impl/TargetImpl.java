package com.itheima.spring.aop.impl;

import com.itheima.spring.aop.proxy.TargetInterface;
import org.springframework.stereotype.Component;

@Component
public class TargetImpl implements TargetInterface
{
    @Override
    public void save() {
        System.out.println("save running......");

//        int res = 1 / 0;
    }
}
