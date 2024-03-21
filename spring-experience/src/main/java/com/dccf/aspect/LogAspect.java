package com.dccf.aspect;


import com.dccf.spring.handwritten.anno.JAfterReturning;
import com.dccf.spring.handwritten.anno.JAfterThrowing;
import com.dccf.spring.handwritten.anno.JAspect;
import com.dccf.spring.handwritten.anno.JBefore;

@JAspect
public class LogAspect
{
    @JBefore("public .* com.dccf.service..*ServiceImpl..*(.*)")
    public void before() {

    }

    @JAfterReturning("public .* com.dccf.service..*ServiceImpl..*(.*)")
    public void afterReturning() {

    }

    @JAfterThrowing("public .* com.dccf.service..*ServiceImpl..*(.*)")
    public void afterThowing() {

    }
}
