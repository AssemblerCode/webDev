package com.dccf.spring.handwritten.aop.impl;

import com.dccf.spring.handwritten.aop.JAdvisedSupport;
import com.dccf.spring.handwritten.aop.JAopProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JJdkDynamicAopProxy implements InvocationHandler, JAopProxy {

  private JAdvisedSupport support;

  public JAdvisedSupport getSupport() {
    return support;
  }

  public void setSupport(JAdvisedSupport support) {
    this.support = support;
  }

  public JJdkDynamicAopProxy(JAdvisedSupport support) {
    this.support = support;
  }

  public JJdkDynamicAopProxy() {}

  @Override
  public Object getProxy() {
    return Proxy.newProxyInstance(
        this.getClass().getClassLoader(),
        this.getSupport().getCls().getInterfaces(),
        this); // 如果你实现了InvocationHandler接口的invoke方法就可以传this,否则需要传入匿名内部类
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      //            执行前置通知
      //            执行环绕通知
//      method.invoke(); // 执行目标方法
      //            执行环绕通知
      //    执行返回通知
    } catch (Exception e) {
//      执行异常通知
      e.printStackTrace();
    }

    return null;
  }
}
