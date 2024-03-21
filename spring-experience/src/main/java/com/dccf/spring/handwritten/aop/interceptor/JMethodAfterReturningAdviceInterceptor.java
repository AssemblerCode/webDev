package com.dccf.spring.handwritten.aop.interceptor;

import com.dccf.spring.handwritten.aop.impl.JAbstractAspectAdvice;

import java.lang.reflect.Method;

/**
 * 前置通知拦截器
 */
public class JMethodAfterReturningAdviceInterceptor extends JAbstractAspectAdvice {

    public JMethodAfterReturningAdviceInterceptor() {
    }

    /**
     *
     * @param aspect 切面类对象
     * @param adviceMethod 通知方法
     */
    public JMethodAfterReturningAdviceInterceptor(Object aspect, Method adviceMethod) {
        super(aspect, adviceMethod);
    }
}
