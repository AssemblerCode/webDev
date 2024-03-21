package com.dccf.context;

import com.dccf.beans.JBeanFactory;

public class JApplicationContext implements JBeanFactory {
  @Override
  public Object getBean(String beanName) {
    return null;
  }

  @Override
  public <T> T  getBean(String beanName ,Class<T>  cls) {
    return null;
  }
}
