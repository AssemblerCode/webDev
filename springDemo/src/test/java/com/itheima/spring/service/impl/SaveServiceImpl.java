package com.itheima.spring.service.impl;

import com.itheima.spring.service.SaveService;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Data
@Scope
public class SaveServiceImpl implements SaveService {
    @Autowired
    @Qualifier("c3p0DataSouce")
    private ComboPooledDataSource source;

    @Value("${driverClass}")
    private String str;

    @Override
    public void save() {
        String format="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateToStr = sdf.format(new Date());
        System.out.println("dateToStr:"+dateToStr);
    }

    @PreDestroy
    public void destory(){
        System.out.println("destory");
    }

    @PostConstruct
    public void init(){
        System.out.println("init");
    }
}
