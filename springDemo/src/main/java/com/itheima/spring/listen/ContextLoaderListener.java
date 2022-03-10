package com.itheima.spring.listen;

import com.itheima.spring.config.StartSpring;
import lombok.Data;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Data
public class ContextLoaderListener implements ServletContextListener {
    private AnnotationConfigApplicationContext ctx;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ctx = StartSpring.getCtx();
        System.out.println("初始化容器");
        ServletContext context = sce.getServletContext();
        context.setAttribute("annoCtx",ctx);
        String configFile = context.getInitParameter("application");
        ClassPathXmlApplicationContext cpac = new ClassPathXmlApplicationContext(configFile);
        context.setAttribute("ClassPathXmlApplicationContext",cpac);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("销毁容器");
        ctx.close();
        ServletContext cont = sce.getServletContext();
        ClassPathXmlApplicationContext context =(ClassPathXmlApplicationContext) cont.getAttribute("ClassPathXmlApplicationContext");
        context.close();
    }
}
