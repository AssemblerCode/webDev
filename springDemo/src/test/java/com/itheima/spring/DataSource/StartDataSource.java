package com.itheima.spring.DataSource;

import com.itheima.spring.config.DbConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.Data;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Data
public class StartDataSource {
    private static final ClassPathXmlApplicationContext CTX = new ClassPathXmlApplicationContext("application.xml");

    private static final DbConfig CFG = new DbConfig();

    private String fileName;

    @Test
    public void test2() throws SQLException {
        ComboPooledDataSource ds = CTX.getBean("c3p0DataSouce", ComboPooledDataSource.class);
        ComboPooledDataSource ds123 = CTX.getBean("c3p0DataSouce123", ComboPooledDataSource.class);
        Connection conn = ds.getConnection();
        System.out.println("conn" + conn);
        conn.close();
        ds.close();
        System.out.println(new DbConfig());
        System.out.println(ds.equals(ds123));
    }

    private String getProperty(String key) throws IOException {
        Properties p = new Properties();
        InputStream is = this.getClass().getResourceAsStream("/dbConfig.properties");
        p.load(is);
        String val = p.getProperty(key);
        is.close();
        return val;
    }

    @Test
    public void test1() throws SQLException, PropertyVetoException, IOException {
        String config = "dbConfig.properties";
        this.setFileName("/" + config);
        String driverClass = getProperty("driverClass");
        String url = getProperty("url");
        String userName = getProperty("user");
        String pwd = getProperty("password");
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setDriverClass(driverClass);
        ds.setJdbcUrl(url);
        ds.setUser(userName);
        ds.setPassword(pwd);
        Connection conn = ds.getConnection();
        conn.close();
        ds.close();
    }
}
