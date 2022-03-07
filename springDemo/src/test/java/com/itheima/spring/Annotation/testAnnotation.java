package com.itheima.spring.Annotation;

import com.itheima.spring.service.SaveService;
import com.itheima.spring.service.impl.SaveServiceImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Data;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.SQLException;

@Data
public class testAnnotation {
    private static final ClassPathXmlApplicationContext CTX = new ClassPathXmlApplicationContext("application.xml");

    @Test
    public void test1() {
        SaveService saveService = CTX.getBean("saveServiceImpl", SaveServiceImpl.class);
        System.out.println(saveService);
        CTX.close();
    }
    @Test
    public void test2() {
        ComboPooledDataSource ds = CTX.getBean("getDataSource", ComboPooledDataSource.class);
        Connection conn = null;
        try {
            conn = ds.getConnection();
            System.out.println("conn"+conn);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ds.close();
    }
}
