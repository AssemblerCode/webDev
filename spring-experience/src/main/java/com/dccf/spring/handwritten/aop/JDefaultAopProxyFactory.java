package com.dccf.spring.handwritten.aop;

import com.dccf.spring.handwritten.aop.impl.JCglibAopProxy;
import com.dccf.spring.handwritten.aop.impl.JJdkDynamicAopProxy;
import org.springframework.aop.framework.AopConfigException;

import java.io.Serializable;

public class JDefaultAopProxyFactory implements Serializable {

  /**
   * 如果你这个目标类实现了接口的话就返回jdk动态代理否则就返回cglib动态代理
   *
   * @param config
   * @return
   * @throws AopConfigException
   */
  public JAopProxy createAopProxy(JAdvisedSupport config) throws AopConfigException {
    return config.getCls().getInterfaces().length > 0
        ? new JJdkDynamicAopProxy(config)
        : new JCglibAopProxy(config);

    /*
    config.isOptimize():
    这可能是在检查某种配置（可能是一个配置对象或设置）是否启用了优化。如果启用了优化，该方法将返回true。

    config.isProxyTargetClass():
    这可能是在检查配置是否指定了使用类代理而不是接口代理。在某些AOP（面向切面编程）框架中，如Spring，你可以选择代理目标类而不是它的接口，这在目标对象没有实现任何接口但你仍然想要对其进行代理时非常有用。

    hasNoUserSuppliedProxyInterfaces(config):
    这个方法名暗示它可能是在检查用户是否没有提供任何代理接口。换句话说，它可能是在确定是否存在要为代理创建的特定接口列表。如果没有提供任何接口，该方法可能会返回true。

    if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
                Class<?> targetClass = config.getTargetClass();
                if (targetClass == null) {
                    throw new AopConfigException("TargetSource cannot determine target class: " +
                            "Either an interface or a target is required for proxy creation.");
                }
                if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
                    return new JdkDynamicAopProxy(config);
                }
                return new ObjenesisCglibAopProxy(config);
            }
            else {
                return new JdkDynamicAopProxy(config);
            }
            */
  }
}
