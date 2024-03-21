package com.dccf.spring.handwritten.anno;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface JAfterThrowing {
    String value() default "";
}
