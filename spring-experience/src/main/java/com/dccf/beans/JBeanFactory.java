package com.dccf.beans;

public interface JBeanFactory  {
  Object getBean(String beanName);
  <T> T getBean(String beanName,Class<T>  cls);
}
