package com.dccf.spring;

public interface InitiallizingBean {
  /**
   * 该方法用来执行bean的初始化
   * @throws Exception
   */
  void afterPropertiesSet() throws Exception;
}
