package com.itheima.spring.config;

import lombok.Data;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ComponentScan("com.itheima.spring.*")

public class SpringConfigAnnotation {
}
