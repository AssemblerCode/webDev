package com.itheima.spring.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration//表示该类是spring的配置类
@ComponentScan("com.itheima.spring.*")//表示开启component-scan扫描 <context:component-scan base-package="com.itheima"/>
@Data

public class SpringConfiguration {

    @Autowired
    private DbConfig cfg;

    @Bean("getDataSource")//将方法的返回值纳入spring,并且bean的id为date
    public DataSource getDataSource() throws PropertyVetoException {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setUser(cfg.getUser());
        ds.setPassword(cfg.getPassword());
        ds.setJdbcUrl(cfg.getJdbcUrl());
        ds.setDriverClass(cfg.getDriverClass());
        return ds;
    }

}
