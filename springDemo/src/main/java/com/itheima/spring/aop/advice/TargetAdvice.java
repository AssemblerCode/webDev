package com.itheima.spring.aop.advice;

public class TargetAdvice {

    public void afterReturn(){
        System.out.println("afterReturn running......");
    }

    public void before(){
        System.out.println("before running......");
    }

}
