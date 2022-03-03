package com.rongke.filter;

import javax.servlet.*;
import java.io.IOException;

public class MyFilterB implements javax.servlet.Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filterB Init");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("我是MyFilterB---开始过滤");
        chain.doFilter(request,response);
        System.out.println("我是MyFilterB---结束过滤");
    }

    @Override
    public void destroy() {
        System.out.println("filterB Destroy");
    }
}
