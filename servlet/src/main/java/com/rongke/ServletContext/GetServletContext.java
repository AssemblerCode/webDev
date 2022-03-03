package com.rongke.ServletContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "getServletContext", value = "/getServletContext")
public class GetServletContext extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
//        第一种方式用的比较普遍
        ServletContext ctx = this.getServletContext();
        ctx = this.getServletConfig().getServletContext();
        ctx = req.getSession().getServletContext();

        ctx.setAttribute("key", "val");
        Object value = ctx.getAttribute("key");
        System.out.println("value="+value);

        String age = ctx.getInitParameter("jinwh");
        Enumeration<String> paramNames = ctx.getInitParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String val = ctx.getInitParameter(name);
            System.out.println("name=" + name);
            System.out.println("val=" + val);
        }

        String path = ctx.getRealPath("web.xml");
        System.out.println("path="+path);

        String ctxPath = ctx.getContextPath();
        System.out.println("ctxPath="+ctxPath);
    }

}
