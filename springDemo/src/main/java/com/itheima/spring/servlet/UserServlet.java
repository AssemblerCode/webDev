package com.itheima.spring.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        AnnotationConfigApplicationContext annoCtx = (AnnotationConfigApplicationContext)ctx.getAttribute("annoCtx");
        System.out.println(annoCtx);
        ClassPathXmlApplicationContext context =(ClassPathXmlApplicationContext) ctx.getAttribute("ClassPathXmlApplicationContext");
        System.out.println(context);
    }
}
