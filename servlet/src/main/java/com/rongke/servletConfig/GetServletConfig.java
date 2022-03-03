package com.rongke.servletConfig;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "GetServletConfig", value = "/GetServletConfig")
public class GetServletConfig extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletConfig cfg = this.getServletConfig();
        String value = cfg.getInitParameter("jinwh");

        Enumeration<String> paramNames = cfg.getInitParameterNames();
        while(paramNames.hasMoreElements()){
            String name = paramNames.nextElement();
            cfg.getInitParameter(name)
        }
    }
}
