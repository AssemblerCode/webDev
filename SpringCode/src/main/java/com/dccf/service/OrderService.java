package com.dccf.service;

import com.dccf.spring.ApplicationContextAware;
import com.dccf.spring.Component;
import com.dccf.spring.JinwhApplicationContext;

@Component("orderService")
public class OrderService implements ApplicationContextAware {
  private JinwhApplicationContext applicationContext;

  @Override
  public void setApplicationContext(JinwhApplicationContext context) {
    this.applicationContext = context;
  }

  public JinwhApplicationContext getApplicationContext() {
    return applicationContext;
  }
}
