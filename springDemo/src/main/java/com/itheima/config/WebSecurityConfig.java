package com.itheima.config;


import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
//@EnableWebSecurity// 开启Spring Security的功能。如果是非springboot项目是需要加上这个注解的，但是springboot项目不需要加这个注解。
public class WebSecurityConfig {
    //TODO 配置安全拦截机制

    /**
     * 创建用户内存管理器
     * 创建一个用户，用户名admin，密码为password，角色为USER
     *
     * @return
     */
//    @Bean
//    public UserDetailsService getUserDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withDefaultPasswordEncoder().username("admin").password("password").roles("USER").build());
//        return manager;
//    }
    @Bean
    public DdlApplicationRunner ddlApplicationRunner(@Autowired(required = false) List ddlList) {
        return new DdlApplicationRunner(ddlList);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        //authorizeRequests()：开启授权保护
        //anyRequest()：对所有请求开启授权保护
        //authenticated()：已认证请求会自动被授权
        //Customizer.withDefaults()：表示使用用户自定义的登录和登出页面
        //formLogin()：开启表单登录(二选一)
        //httpBasic()：开启http基本登录(二选一)
        return security.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .build();
    }
}
