package com.itheima.spring.aop.run.Main;

import com.itheima.spring.aop.advice.TargetAdvice;
import com.itheima.spring.aop.impl.TargetImpl;
import com.itheima.spring.aop.proxy.TargetInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyMain {
    public static void main(String[] args) {
        TargetInterface target = new TargetImpl();
        Class<? extends TargetInterface> cls = target.getClass();
        TargetInterface proxyInstance=(TargetInterface) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                TargetAdvice advice = new TargetAdvice();
                advice.before();
                Object invoke = method.invoke(target, args);
                advice.afterReturn();
                return invoke;
            }
        });
    }
}
