package com.dccf.spring.handwritten.aop;

/** 保存切面类的元数据。类似于BeanDefinition */
public class JAopConfig {
  private String aspectClass; // 切面类的全路径 例如: com.dccf.aspect.LogAspect
  private String beforePointCut; // 前置通知切面表达式 例如: public .* com.dccf.service..*ServiceImpl..*(.*)
  private String afterReturningPointCut; // 后置通知切面表达式 例如: public .* com.dccf.service..*ServiceImpl..*(.*)
  private String afterThrowingPointCut; // 异常通知切面表达式 例如: public .* com.dccf.service..*ServiceImpl..*(.*)
  private String aspectBefore; // 前置通知 before
  private String afterReturning; // 后置通知 afterReturning
  private String afterThrowing; // 异常通知 afterThrowing

  public JAopConfig() {}

  public JAopConfig(
      String aspectClass,
      String pointCut,
      String afterReturningPointCut,
      String afterThrowingPointCut,
      String aspectBefore,
      String afterReturning,
      String afterThrowing) {
    this.aspectClass = aspectClass;
    this.beforePointCut = pointCut;
    this.afterReturningPointCut = afterReturningPointCut;
    this.afterThrowingPointCut = afterThrowingPointCut;
    this.aspectBefore = aspectBefore;
    this.afterReturning = afterReturning;
    this.afterThrowing = afterThrowing;
  }

  @Override
  public String toString() {
    return "JAopConfig{" +
            "aspectClass='" + aspectClass + '\'' +
            ", beforePointCut='" + beforePointCut + '\'' +
            ", afterReturningPointCut='" + afterReturningPointCut + '\'' +
            ", afterThrowingPointCut='" + afterThrowingPointCut + '\'' +
            ", aspectBefore='" + aspectBefore + '\'' +
            ", afterReturning='" + afterReturning + '\'' +
            ", afterThrowing='" + afterThrowing + '\'' +
            '}';
  }

  public void setAspectBefore(String aspectBefore) {
    this.aspectBefore = aspectBefore == null ? "" : this.aspectBefore + aspectBefore;
  }


  public void setAfterReturning(String afterReturning) {
    this.afterReturning = afterReturning == null ? "" : this.afterReturning + afterReturning;
  }


  public void setAfterThrowing(String afterThrowing) {
    this.afterThrowing = afterThrowing == null ? "" : this.afterThrowing + afterThrowing;
  }

  public String getAspectClass() {
    return aspectClass;
  }

  public void setAspectClass(String aspectClass) {
    this.aspectClass = aspectClass;
  }

  public String getBeforePointCut() {
    return beforePointCut;
  }

  public void setBeforePointCut(String beforePointCut) {
    this.beforePointCut = beforePointCut;
  }

  public String getAfterReturningPointCut() {
    return afterReturningPointCut;
  }

  public void setAfterReturningPointCut(String afterReturningPointCut) {
    this.afterReturningPointCut = afterReturningPointCut;
  }

  public String getAfterThrowingPointCut() {
    return afterThrowingPointCut;
  }

  public void setAfterThrowingPointCut(String afterThrowingPointCut) {
    this.afterThrowingPointCut = afterThrowingPointCut;
  }

  public String getAspectBefore() {
    return aspectBefore;
  }

  public String getAfterReturning() {
    return afterReturning;
  }

  public String getAfterThrowing() {
    return afterThrowing;
  }
}
