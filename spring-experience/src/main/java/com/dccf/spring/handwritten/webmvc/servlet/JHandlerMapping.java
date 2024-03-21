package com.dccf.spring.handwritten.webmvc.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class JHandlerMapping {
  // 方法所在的controller
  private Object controller;

  //  url
  private Pattern url;

  //   url绑定的方法对象
  private Method method;

  public Object getController() {
    return controller;
  }

  public void setController(Object controller) {
    this.controller = controller;
  }

  public Pattern getUrl() {
    return url;
  }

  public void setUrl(Pattern url) {
    this.url = url;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public JHandlerMapping(Object controller, Pattern url, Method method) {
    this.controller = controller;
    this.url = url;
    this.method = method;
  }

  public JHandlerMapping() {
  }
}
