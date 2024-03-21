package com.dccf.spring;

public interface BeanNameAware {
  /**
   * 这个方法用来设置bean的name
   *
   * @param beanName
   */
  void setBeanName(String beanName);
}
