package com.itheima.spring.aop.aspact;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect//表示当前类是一个切面类
public class MyAspact {
    @Before("execution(* com.itheima.spring.aop..*.*(..))")
    protected void before() {
        System.out.println("前置增强-----------");
    }
    @AfterReturning("execution(* com.itheima.spring.aop..*.*(..))")
    protected void afterRut() {
        System.out.println("后置增强-----------");
    }
    @Around("execution(* com.itheima.spring.aop..*.*(..))")
    protected Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕前增强-----------");
        Object args[]= pjp.getArgs();
        Object proceed = pjp.proceed(args);//切点方法
        System.out.println("环绕后增强-----------");
        return proceed;
    }
    @AfterThrowing("MyAspact.pointcut()")
//    @AfterThrowing("execution(* com.itheima.spring.aop..*.*(..))")
    protected void Exception() {
        System.out.println("异常增强-----------");
    }
//    @After("execution(* com.itheima.spring.aop..*.*(..))")
    @After("pointcut()")
    protected void FinalLy() {
        System.out.println("最终增强-----------");
    }

    @Pointcut("execution(* com.itheima.spring.aop..*.*(..))")
    public void pointcut(){

    }
}
