package com.dccf.spring;

public interface BeanPostProcessor {
  /**
   * 用来执行bean初始化前要做的操作
   * @param bean
   * @param beanName
   * @return
   */
    Object postProcessBeforeInitialization(Object bean,String beanName);

  /**
   * 用来执行bean初始化后要做的操作
   * @param bean
   * @param beanName
   * @return
   */
  Object postProcessAfterInitialization(Object bean,String beanName);

}
