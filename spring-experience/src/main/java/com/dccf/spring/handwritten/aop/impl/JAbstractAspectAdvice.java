package com.dccf.spring.handwritten.aop.impl;

import com.dccf.spring.handwritten.aop.JAdvice;

import java.lang.reflect.Method;

/** 这是一个默认的通知类. */
public class JAbstractAspectAdvice implements JAdvice {
  private Object aspect; // 表示当前通知类属于那一个切面类 例如LogAspect
  private Method adviceMethod; // 表示切面类中的哪一个方法 例如Before

  public Object getAspect() {
    return aspect;
  }

  public void setAspect(Object aspect) {
    this.aspect = aspect;
  }

  public Method getAdviceMethod() {
    return adviceMethod;
  }

  public void setAdviceMethod(Method adviceMethod) {
    this.adviceMethod = adviceMethod;
  }

  public JAbstractAspectAdvice() {}

  public JAbstractAspectAdvice(Object aspect, Method adviceMethod) {
    this.aspect = aspect;
    this.adviceMethod = adviceMethod;
  }
}
