package com.itheima.spring.aop.run.Main;

import com.itheima.spring.aop.proxy.TargetInterface;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AspactMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");
        TargetInterface target = ctx.getBean("target", TargetInterface.class);
        target.save();
        target.getClass();
        ctx.close();
    }
}
