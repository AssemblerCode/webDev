package com.dccf.spring;

/** 该类主要就是用来存放bean的一些相关属性用的 */
public class BeanDefinition {
  /** bean的类型 */
  private Class cls;

  /** bean是单例还是多例 */
  private String scope;

  /** bean的name */
  private String beanName;

  public Class getCls() {
    return cls;
  }

  public void setCls(Class cls) {
    this.cls = cls;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getBeanName() {
    return beanName;
  }

  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }

  public BeanDefinition(Class cls, String scope, String beanName) {
    this.cls = cls;
    this.scope = scope;
    this.beanName = beanName;
  }

  public BeanDefinition() {}
}
