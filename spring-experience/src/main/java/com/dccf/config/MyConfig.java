package com.dccf.config;

import com.dccf.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

  @Bean
  public Person getPerson() {
    Person p = new Person("jinwh", 26);
    return p;
  }
}
