package com.dccf.spring.handwritten.aop;

import com.dccf.spring.handwritten.aop.interceptor.JMethodBeforeAdviceInterceptor;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

public class JAdvisedSupport {
  private Map<Method, List<JAdvice>> methodAdvices = new HashMap<>();

  // 目标类的对象
  private Object instance;

  // 目标类的class
  private Class<?> cls;

  // 切面类的aopConfig信息
  private JAopConfig config;

  //  用来维护切面类的正则表达式
  private Pattern pointCutClassPattern;

  /**
   * 判断一下目标方法到底要执行那几个通知,就是把目标方法和他的通知绑定起来。 等于就是一个目标Method对象绑定符合表达式的所有通知,例如 method =>
   * before,returning,throwing
   */
  public void methodAndAdvices() {
    Pattern compile = Pattern.compile(getPointForClassRegex(this.config.getBeforePointCut()));
    try {
      Class<?> aspectCls = Class.forName(this.getConfig().getAspectClass());
      Map<String, Method> nameMethods = new HashMap<>();
      Method[] methodList = aspectCls.getMethods(); // 拿到切面类的所有方法
      for (Method m : methodList) {
        //        保存切面类中的方法名称和Method对象
        nameMethods.put(m.getName(), m);
      }
      for (Method m : this.getCls().getMethods()) {
        // 判断目标类中的所有方法抛出的异常全部去除然后找到符合切面表达式的方法并且往methodAdvice里面保存
        String methodToStr = m.toString();
        methodToStr =
            methodToStr.contains("throws")
                ? methodToStr.substring(0, methodToStr.lastIndexOf("throws")).trim()
                : methodToStr;
        if (compile.matcher(methodToStr).matches()) {
          List<JAdvice> jAdvices = new ArrayList<>(); // 这个集合就是用来保存一个方法的通知对象用的
          Object aspectInstance = aspectCls.newInstance(); // 拿到切面类的对象
          String aspectBefore = getConfig().getAspectBefore();
          if (aspectBefore != null && !("".equals(aspectBefore.trim()))) {
            Method method = nameMethods.get(aspectBefore);

            //          如果有前置通知
            JMethodBeforeAdviceInterceptor interceptor =
                new JMethodBeforeAdviceInterceptor(aspectInstance, method);
            jAdvices.add(interceptor);
          }
          methodAdvices.put(m, jAdvices);
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }
  }

  private boolean getPattern(String pointCutRegex) {
    return Pattern.compile("class " + pointCutRegex.substring(pointCutRegex.lastIndexOf(" " + 1)))
        .matcher(cls.toString())
        .matches();
  }

  private String getPointForClassRegex(String pointCutRegex) {
    /*
        原始字符串："public .* com.dccf.service..*ServiceImpl..*(.*)"
    .replaceAll("\\.", "\\\\.")：这一步是将原始字符串中的所有点号（.）替换为双反斜线加点号（\.）。在正则表达式中，点号表示任意字符，所以为了表示字面量的点号，我们需要对其进行转义。
    替换后的字符串："public \.* com\.dccf\.service\.\.*ServiceImpl\.\.*\(.*\)"
    但这里有个小错误，原始字符串中的..*应该是.*来表示任意数量的任意字符。如果真的是..*，则第一个替换操作可能不会按预期进行。这里我假设原始字符串中的..*是个误写，实际上应该是.*
    。所以更合理的原始字符串应该是："public .* com.dccf.service.*ServiceImpl.*\(.*\)"
    基于这个更正，替换后的字符串应该是："public \.* com\.dccf\.service\.*ServiceImpl\.*\(.*\)"
    .replaceAll("\\\\.\\*", ".*")：这一步的目的是将上一步替换结果中的\.*（实际上是表示字面量的.后面跟着*）替换为.*（正则表达式中的任意数量的任意字符）。
    但这里有个问题，上一步替换后的字符串中并没有\\.*这样的模式，而是\.*。所以这个替换实际上不会做任何事情。
    正确的替换应该是针对\.*，因此这一步应该是：.replaceAll("\\.", "\\\\.").replaceAll("\\.\\*", ".*")
    但这样的话，第一步和第二步就有些冗余了。更合理的做法是先进行其他替换，最后再进行点号的转义。
    .replaceAll("\\(", "\\\\(") 和 .replaceAll("\\)", "\\\\)")：这两步是将原始字符串中的左右括号替换为转义后的左右括号。这是因为在正则表达式中，括号是特殊字符，用于分组。
    替换后的字符串（基于更正的原始字符串和只考虑这两步替换）："public .* com.dccf.service.*ServiceImpl.*\\(.*\\)"
    综合以上分析，代码片段的目的似乎是想将一个描述方法签名的字符串转换为一个正则表达式，以便后续匹配。但是，这个代码片段存在一些逻辑上的问题，可能不会按预期工作。正确的处理顺序和替换操作需要根据具体的需求来确定。
         */
    return pointCutRegex
        .replaceAll("\\.", "\\\\.") // 将点号替换为转义后的点号
        .replaceAll("\\*", ".*") // 将星号替换为任意字符序列（这里不需要双斜线转义星号）
        .replaceAll("\\(", "\\\\(") // 将左括号替换为转义后的左括号
        .replaceAll("\\)", "\\\\)") // 将右括号替换为转义后的右括号
        .substring(
            0,
            pointCutRegex.lastIndexOf(
                "\\(")); //    截取这段字符串:public .* com\.dccf\.service\..*ServiceImpl
  }

  /**
   * 判断切面类是否符合切面表达式
   *
   * @return
   */
  public boolean pointCutMatchClass() {
    String beforePointForClassRegex = getPointForClassRegex(getConfig().getBeforePointCut());
    String afterReturnPointForClassRegex =
        getPointForClassRegex(getConfig().getAfterReturningPointCut());
    String afterThrowingPointForClassRegex =
        getPointForClassRegex(getConfig().getAfterThrowingPointCut());
    List<String> regexList =
        Arrays.asList(
            /*
             如果你有一个逗号分隔的字符串 "apple,banana,orange"，使用这个方法后，你将得到一个字符串数组 ["apple","banana", "orange"]。
            */
            StringUtils.commaDelimitedListToStringArray(
                beforePointForClassRegex
                    + ","
                    + afterReturnPointForClassRegex
                    + ","
                    + afterThrowingPointForClassRegex));
    for (String regex : regexList) if (getPattern(regex)) return true;
    return false;
  }

  public Pattern getPattern() {
    return pointCutClassPattern;
  }

  public void setPattern(Pattern pointCutClassPattern) {
    this.pointCutClassPattern = pointCutClassPattern;
  }

  public Object getInstance() {
    return instance;
  }

  public void setInstance(Object instance) {
    this.instance = instance;
  }

  public Class<?> getCls() {
    return cls;
  }

  public void setCls(Class<?> cls) {
    this.cls = cls;
  }

  public JAopConfig getConfig() {
    return config;
  }

  public void setConfig(JAopConfig config) {
    this.config = config;
  }

  public Map<Method, List<JAdvice>> getMethodListMap() {
    return methodAdvices;
  }

  public void setMethodListMap(Map<Method, List<JAdvice>> methodListMap) {
    this.methodAdvices = methodListMap;
  }

  public Pattern getPointCutClassPattern() {
    return pointCutClassPattern;
  }

  public void setPointCutClassPattern(Pattern pointCutClassPattern) {
    this.pointCutClassPattern = pointCutClassPattern;
  }

  public JAdvisedSupport() {}

  public JAdvisedSupport(Object instance, Class<?> cls, JAopConfig config) {
    this.instance = instance;
    this.cls = cls;
    this.config = config;
    //    String beforePointCut = getConfig().getBeforePointCut();
    //    String afterReturningPointCut = getConfig().getAfterReturningPointCut();
    //    String afterThrowingPointCut = getConfig().getAfterThrowingPointCut();

  }

  @Override
  public String toString() {
    return "JAdvisedSupport{"
        + "instance="
        + instance
        + ", cls="
        + cls
        + ", config="
        + config
        + ", pointCutClassPattern="
        + pointCutClassPattern
        + '}';
  }
}
