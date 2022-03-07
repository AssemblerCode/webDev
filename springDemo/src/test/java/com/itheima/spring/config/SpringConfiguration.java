package com.itheima.spring.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration//表示该类是spring的配置类
@ComponentScan("com.itheima.spring.*")//表示开启component-scan扫描 <context:component-scan base-package="com.itheima"/>
@Import(String.class)//@Import表示去加载其他配置类,相当于使用import标签去导入其他xml配置文件 <import resource="abc.xml"/>
@PropertySource("dbConfig.properties")
@Data

public class SpringConfiguration {

    @Autowired
    private DbConfig cfg;

    @Bean("getDataSource")//将方法的返回值纳入spring,并且bean的id为getDataSource
    public DataSource getDataSource() throws PropertyVetoException {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setUser(cfg.getUser());
        ds.setPassword(cfg.getPassword());
        ds.setJdbcUrl(cfg.getJdbcUrl());
        ds.setDriverClass(cfg.getDriverClass());
        return ds;
    }

}
