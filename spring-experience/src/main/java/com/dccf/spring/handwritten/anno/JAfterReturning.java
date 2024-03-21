package com.dccf.spring.handwritten.anno;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface JAfterReturning {
    String value() default "";
}
