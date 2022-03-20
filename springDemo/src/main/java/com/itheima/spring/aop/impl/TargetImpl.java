package com.itheima.spring.aop.impl;

import com.itheima.spring.aop.proxy.TargetInterface;

public class TargetImpl implements TargetInterface
{
    @Override
    public void save() {
        System.out.println("save running......");

    }
}
