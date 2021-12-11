package com.rongke.serv;

import javax.servlet.*;
import java.io.IOException;

/**
 * servlet的继承体系
 */
public class MyServlet implements Servlet {
    /**
     * 该方法用来在tomcat在启动时对该servlet进行初始化用的
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * servlet处理请求的方法
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    /**
     * 该方法是tomcat关闭的时候对servlet进行销毁的时候用的方法
     */
    @Override
    public void destroy() {

    }
}
