package com.dccf.spring.handwritten.webmvc.servlet;

import java.util.Map;

public class JModelAndView {
  private String viewName; // 视图名称
  private Map<String, Object> model; // model

  public JModelAndView(String viewName, Map<String, Object> model) {
    this.viewName = viewName;
    this.model = model;
  }

  public JModelAndView() {
  }

  public String getViewName() {
    return viewName;
  }

  public void setViewName(String viewName) {
    this.viewName = viewName;
  }

  public Map<String, Object> getModel() {
    return model;
  }

  public void setModel(Map<String, Object> model) {
    this.model = model;
  }
}
