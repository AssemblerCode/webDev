package com.itheima.spring.config.Main;

import com.itheima.spring.config.SpringConfigAnnotation;
import lombok.Data;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Data
public class StartSpring {
    private static final AnnotationConfigApplicationContext CTX = new AnnotationConfigApplicationContext(SpringConfigAnnotation.class);

    public static AnnotationConfigApplicationContext getCtx(){
        return  CTX;
    }

}
