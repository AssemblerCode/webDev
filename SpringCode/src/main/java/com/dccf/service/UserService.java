package com.dccf.service;

import com.dccf.spring.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component("userService")
@Transaction
public class UserService implements BeanNameAware, InitiallizingBean, BeanPostProcessor,UserInterface {

  @Autowired private OrderService orderService;

  private String beanName;
  private String name;


  public void test() {
    System.out.println("os=>" + orderService);
    System.out.println("beanName=>" + beanName);
  }

  @Override
  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("afterPropertiesSet=>" + System.currentTimeMillis());
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    Class  load=UserService.class;
    if ("userService".equals(beanName)) {//实际上这里的代码主要是判断你这个bean是否要进行后置通知的拦截
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
}
