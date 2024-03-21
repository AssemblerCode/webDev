package com.dccf.spring;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class JinwhBeanPostProcessor implements BeanPostProcessor,UserInterface {
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) {
    System.out.println("bean初始化前=>");
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    System.out.println("bean初始化后=>");
    if ("JinwhBeanPostProcessor".equals(beanName)) {//实际上这里的代码主要是判断你这个bean是否要进行后置通知的拦截
      Class  load  = JinwhBeanPostProcessor.class ;
      Object instance =
          Proxy.newProxyInstance(
              load.getClassLoader(),
              load.getInterfaces(),
              new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                  System.out.println("开始执行aop代理");
                  return method.invoke(bean,args);
                }
              });
      return instance;
    }
    return bean;
  }

  @Override
  public void test() {

  }
}
