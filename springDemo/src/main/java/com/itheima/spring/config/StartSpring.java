package com.itheima.spring.config;

import lombok.Data;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@Data
public class StartSpring {
    private static final AnnotationConfigApplicationContext CTX = new AnnotationConfigApplicationContext(SpringConfigAnnotation.class);

    @Bean
    public static AnnotationConfigApplicationContext getCtx(){
        return  CTX;
    }

}
