package com.dccf.spring.handwritten.aop.impl;

import com.dccf.spring.handwritten.aop.JAdvisedSupport;
import com.dccf.spring.handwritten.aop.JAopProxy;

public class JCglibAopProxy implements JAopProxy {

  private JAdvisedSupport support;

  public JAdvisedSupport getSupport() {
    return support;
  }

  public void setSupport(JAdvisedSupport support) {
    this.support = support;
  }

  public JCglibAopProxy() {}

  public JCglibAopProxy(JAdvisedSupport support) {
    this.support = support;
  }

  @Override
  public Object getProxy() {
    return null;
  }
}
