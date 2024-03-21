package com.dccf.beans;

import com.dccf.spring.handwritten.anno.JResource;
import com.dccf.spring.handwritten.aop.JAdvisedSupport;
import com.dccf.spring.handwritten.aop.JAopConfig;
import com.dccf.spring.handwritten.aop.JDefaultAopProxyFactory;
import com.dccf.spring.util.CommonUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletConfig;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JApplicationContext implements JBeanFactory {

  private final Map<String, JBeanDefinition> BEAN_DEFINITION_MAP = new ConcurrentHashMap<>();
  private final Map<String, Object> SINGEL_OBJ_MAP = new ConcurrentHashMap<>();
  private JBeanDefinitionReader reader ;
  private String contextConfigLocation;

  public JBeanDefinitionReader getReader() {
    return reader;
  }

  public void setReader(JBeanDefinitionReader reader) {
    this.reader = reader;
  }

  public String getContextConfigLocation() {
    return contextConfigLocation;
  }

  public void setContextConfigLocation(String contextConfigLocation) {
    this.contextConfigLocation = contextConfigLocation;
  }

  private Set<String> getBeanNames() {
    return this.BEAN_DEFINITION_MAP.keySet();
  }

  public int getBeanDefinitionCount() {
    return BEAN_DEFINITION_MAP.size();
  }

  private void populateBean() throws IllegalAccessException {
    if (!CollectionUtils.isEmpty(SINGEL_OBJ_MAP)) {
      Set<String> keys = this.SINGEL_OBJ_MAP.keySet();
      for (String k : keys) {
        Object o = SINGEL_OBJ_MAP.get(k);
        for (Field f : o.getClass().getDeclaredFields()) {
          if (!f.isAnnotationPresent(JResource.class)) {
            continue;
          }
          JResource jResourceAnno = f.getAnnotation(JResource.class);
          String beanName = jResourceAnno.value().trim();
          if (StringUtils.isEmpty(beanName)) {
            beanName = CommonUtil.capitalize(beanName);
          }
          Object instance = this.SINGEL_OBJ_MAP.get(beanName);
          f.setAccessible(true);
          f.set(o, instance);
        }
      }
    }
  }

  private Object instantiateBean(String beanName, JBeanDefinition beanDef) {
    String className = beanDef.getBeanClassName();
    Object instance = null;
    try {
      Class<?> cls = Class.forName(className);
      instance = cls.newInstance();
      JBeanDefinitionReader read  = getReader();
      List<JAopConfig> configList = read.getAopConfigs();
      for (JAopConfig config : configList) {
        JAdvisedSupport support = new JAdvisedSupport(instance,cls,config);
        Object proxyInstance = new JDefaultAopProxyFactory().createAopProxy(support).getProxy();//获取动态代理对象
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      return instance;
    }
  }

  private void doIoc() {
    Set<String> keys = this.BEAN_DEFINITION_MAP.keySet();
    for (String k : keys) {
      JBeanDefinition beanDef = BEAN_DEFINITION_MAP.get(k);
      String className = beanDef.getBeanClassName();
      if (!beanDef.isLazyInit()) {
        //        表示非懒加载的bean
        Object bean = getBean(className);
      }
    }
  }

  private void doBeanDefinition(List<JBeanDefinition> beanDefinitionList) {
    if (CollectionUtils.isEmpty(beanDefinitionList)) return;
    for (JBeanDefinition beanDef : beanDefinitionList) {
      String beanName = beanDef.getBeanName();
      if (!BEAN_DEFINITION_MAP.containsKey(beanName)) BEAN_DEFINITION_MAP.put(beanName, beanDef);
    }
  }

  public JApplicationContext() {}

  public JApplicationContext(ServletConfig config) {
    this(config.getInitParameter("contextConfigLocation"));
  }

  public JApplicationContext(String config) {
    try {
      setReader(new JBeanDefinitionReader(config));
      setContextConfigLocation(config);
      List<JBeanDefinition> beanDefinitionList = reader.getBeanDefinitionList();
      doBeanDefinition(beanDefinitionList);
      doIoc();
      populateBean();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Object getBean(String beanName) {
    Object instance = null;
    if (this.SINGEL_OBJ_MAP.containsKey(beanName)) {
      JBeanDefinition beanDef = this.BEAN_DEFINITION_MAP.get(beanName);
      instance = instantiateBean(beanName, beanDef);
    }
    return instance;
  }

  @Override
  public <T> T getBean(String beanName, Class<T> cls) {
//    ClassPathXmlApplicationContext cpxa = new ClassPathXmlApplicationContext();
//    cpxa.getBean(beanName);
    Object bean = getBean(beanName);

    /*
        isInstance方法表示如果给定的对象是该Class对象所表示的类或接口的实例（包括直接实例和间接实例），或是该Class对象所表示的类或接口的数组元素类型，那么isInstance()方法会返回true，否则返回false。
       这样，你就可以在不进行强制类型转换的情况下，检查一个对象的类型是否符合某个Class对象所表示的类型。
    */
    if (bean != null && cls.isInstance(bean)) return (T) bean;

    /*
    强制类型转换在编译时检查类型，并在运行时执行转换。这个方法实际上是调用对象的getClass()方法来获取对象的运行时类，并使用该类进行类型转换。
    cast()方法与直接使用强制类型转换的主要区别在于，cast()方法会在运行时检查类型，而强制类型转换在编译时检查类型。
    如果尝试使用cast()方法将一个不兼容的对象转型为指定类型，会抛出ClassCastException。
    总结：强制类型转换和Class类的cast()方法都用于类型转换，但它们的运行时行为和用途略有不同。强制类型转换主要用于在编译时进行明确的类型指定，而cast()方法用于在运行时根据对象的实际类型进行动态类型转换。
         return cls.cast(bean);
    */
    throw new IllegalArgumentException(
        "The bean with name " + beanName + " is not of type " + cls.getName());
  }
}
