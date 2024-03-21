package com.dccf.spring;

public interface ApplicationContextAware {
  /**
   * 该方法用于设置spring容器本身。
   * @param context spring容器
   */
  void setApplicationContext(JinwhApplicationContext context);
}
