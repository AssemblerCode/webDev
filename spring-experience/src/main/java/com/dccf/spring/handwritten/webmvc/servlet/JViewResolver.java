package com.dccf.spring.handwritten.webmvc.servlet;

import java.io.File;

public class JViewResolver {
  private static final String DEFAULT_TEMPLATE_SUFFIX = ".html"; // 表示解析文件的后缀(可以有多个)
  private File templateRootDir; // 模板文件所在目录

  public String getDEFAULT_TEMPLATE_SUFFIX() {
    return DEFAULT_TEMPLATE_SUFFIX;
  }

  public File getTemplateRootDir() {
    return templateRootDir;
  }

  public void setTemplateRootDir(String templateRootDir) {
    setTemplateRootDir(new File(templateRootDir));
  }

  public void setTemplateRootDir(File templateRootDir) {
    this.templateRootDir = templateRootDir;
  }

  public JViewResolver() {}

  public JViewResolver(String templateRootDir) {
    this(new File(templateRootDir));
  }

  public JViewResolver(File templateRootDir) {
    this.templateRootDir = templateRootDir;
  }

  /**
   * 根据视图名称获取视图
   *
   * @param viewName 视图名称
   * @return 视图
   */
  public JView resolverViewName(String  viewName) {
    viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName : viewName + DEFAULT_TEMPLATE_SUFFIX;
    String targetFile = (templateRootDir.getPath() + "/" + viewName).replaceAll("/+","/");
    return new JView(targetFile);
  }
}
