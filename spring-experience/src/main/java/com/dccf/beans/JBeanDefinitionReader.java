package com.dccf.beans;

import com.dccf.spring.handwritten.anno.*;
import com.dccf.spring.handwritten.aop.JAopConfig;
import com.dccf.spring.util.CommonUtil;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.util.StringUtils;

import javax.servlet.ServletConfig;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JBeanDefinitionReader {

  private List<String> classNames = Collections.synchronizedList(new ArrayList<>());

  private List<JAopConfig> aopConfigs = Collections.synchronizedList(new ArrayList<>());

  private List<JBeanDefinition> beanDefinitionList;

  public List<JAopConfig> getAopConfigs() {
    return aopConfigs;
  }

  public void setAopConfigs(List<JAopConfig> aopConfigs) {
    this.aopConfigs = aopConfigs;
  }

  private void isContainsAopAnnotation(Class cls, JAopConfig config) {
    List<Method> methods = Arrays.asList(cls.getDeclaredMethods());
    for (Method m : methods) {
      if (m.isAnnotationPresent(JBefore.class)) {
        JBefore anno = m.getAnnotation(JBefore.class);
        config.setAspectBefore(m.getName() + ";");
        config.setBeforePointCut(anno.value() + ";");
      } else if (m.isAnnotationPresent(AfterReturning.class)) {
        JAfterReturning anno = m.getAnnotation(JAfterReturning.class);
        config.setAfterReturning(m.getName() + ";");
        config.setAfterReturningPointCut(anno.value()+ ";");
      } else if (m.isAnnotationPresent(JAfterThrowing.class)) {
        JAfterThrowing anno = m.getAnnotation(JAfterThrowing.class);
        config.setAfterThrowing(m.getName() + ";");
        config.setAfterThrowingPointCut(anno.value()  + ";");
      }
    }
  }

  public List<JBeanDefinition> getBeanDefinitionList() {
    return beanDefinitionList;
  }

  public void setBeanDefinitionList(List<JBeanDefinition> beanDefinitionList) {
    this.beanDefinitionList = beanDefinitionList;
  }

  private void recursionFile(File classPath, String basePackage) {
    File[] classList = classPath.listFiles();
    for (File file : classList) {
      if (file.isDirectory()) {
        //        说明是一个文件夹,那就继续递归
        recursionFile(file, basePackage);
      } else {
        if (file.getName().endsWith(".class")) {
          String className = basePackage + "." + file.getName().replace(".class", "");
          classNames.add(className);
        }
      }
    }
  }

  private void doConfig(String contextConfigLocation) {
    try {
      String basePackage = CommonUtil.getPropertiesField(contextConfigLocation, "basePackage");
      URL url = this.getClass().getClassLoader().getResource(basePackage.replace(".", "/"));
      File classPath = new File(url.getFile());
      recursionFile(classPath, basePackage);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 1.遍历classNames,通过反射Class.forName方法得到每一个class文件的对象
   * 2.判断class对象里面是否包含这些注解: @JController、@JService、@JRepository、@JComponent
   * 3.如果包含这些注解就将其作为一个BeanDefinition对象纳入spring容器
   */
  private List<JBeanDefinition> loadBeanDefinitions() {
    List<JBeanDefinition> definitionList = new ArrayList<>();

    try {
      if (!classNames.isEmpty()) {
        for (String name : classNames) {
          Class<?> cls = Class.forName(name);
          if (cls.isInterface()) // 如果这个类是一个接口就不创建对象
          continue;

          JBeanDefinition def = null;
          String beanName = "";
          if (cls.isAnnotationPresent(JController.class)) {
            JController jControllerAnno = cls.getAnnotation(JController.class);
            beanName = jControllerAnno.value();
            //            if (StringUtils.isEmpty(value)) {
            //              /*
            //              getSimpleName() 是 Java 中的一个方法，它属于 java.lang.reflect.Name
            // 接口。这个方法用于获取一个类型的简单名称。
            //               具体来说，对于基本类型，它返回对应的名称（例如，对于 int，它返回 "int"）。对于类和接口，它返回不包括任何包名的名称（例如，对于
            // java.util.List，它返回 "List"）。
            //               以下是一些示例：
            //               System.out.println(int.class.getSimpleName());  // 输出 "int"
            //               System.out.println(String.class.getSimpleName());  // 输出 "String"
            //               System.out.println(java.util.List.class.getSimpleName());  // 输出 "List"
            //               注意：这个方法主要用于反射和某些工具类，例如 org.apache.commons.lang3.reflect.TypeUtils。在常规的
            // Java 编程中，你可能不会经常使用它。
            //              */
            //              value = cls.getSimpleName();
            //              beanName = value.substring(0, 1).toLowerCase() + value.substring(1); //
            // 首字母小写
            //            }
            //
            //            //            这里只做创建beanDefinition这件事情,创建bean实例交给getBean来做.
            //            JBeanDefinition def = new JBeanDefinition();
            //            def.setBeanName(beanName);
            //            def.setBeanClassName(cls.getName());
            def = newJBeanDefinition(beanName, cls);
            definitionList.add(def);
          } else if (cls.isAnnotationPresent(JService.class)) {
            JService jServiceAnno = cls.getAnnotation(JService.class);
            beanName = jServiceAnno.value();
            def = newJBeanDefinition(beanName, cls);
            definitionList.add(def);

            //            这个service可能会实现接口,把接口名称首字母小写.对象实例用同一个就好.
            for (Class<?> clsInterface : cls.getInterfaces()) {
              JBeanDefinition definition = new JBeanDefinition();
              definition.setBeanName(CommonUtil.capitalize(clsInterface.getSimpleName()));
              definition.setBeanClassName(cls.getName());
              definitionList.add(definition);
            }
          } else if (cls.isAnnotationPresent(JAspect.class)) {
            //            匹配到切面类了
            System.out.println("找到切面类了");
            JAopConfig config = new JAopConfig();
            config.setAspectClass(cls.getName());
            isContainsAopAnnotation(cls, config);
            aopConfigs.add(config);
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    setBeanDefinitionList(definitionList);
    return definitionList;
  }

  private JBeanDefinition newJBeanDefinition(String value, Class<?> cls) {
    if (StringUtils.isEmpty(value)) {
      /*
      getSimpleName() 是 Java 中的一个方法，它属于 java.lang.reflect.Name 接口。这个方法用于获取一个类型的简单名称。
       具体来说，对于基本类型，它返回对应的名称（例如，对于 int，它返回 "int"）。对于类和接口，它返回不包括任何包名的名称（例如，对于 java.util.List，它返回 "List"）。
       以下是一些示例：
       System.out.println(int.class.getSimpleName());  // 输出 "int"
       System.out.println(String.class.getSimpleName());  // 输出 "String"
       System.out.println(java.util.List.class.getSimpleName());  // 输出 "List"
       注意：这个方法主要用于反射和某些工具类，例如 org.apache.commons.lang3.reflect.TypeUtils。在常规的 Java 编程中，你可能不会经常使用它。
      */
      value = CommonUtil.capitalize(cls.getSimpleName()); // 首字母小写
    }

    //            这里只做创建beanDefinition这件事情,创建bean实例交给getBean来做.
    JBeanDefinition def = new JBeanDefinition();
    def.setBeanName(value);
    def.setBeanClassName(cls.getName());
    return def;
  }

  public JBeanDefinitionReader(String... contextConfigLocation) {
    doConfig(contextConfigLocation[0]);
    loadBeanDefinitions();
  }

  public JBeanDefinitionReader(ServletConfig config) {}
}
