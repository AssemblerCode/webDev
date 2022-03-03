package com.rongke.filter;

import javax.servlet.*;
import java.io.IOException;

public class MyFilterA implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filterA Init");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("我是MyFilterA---开始过滤");
        chain.doFilter(request,response);
        System.out.println("我是MyFilterA---结束过滤");
    }

    @Override
    public void destroy() {
        System.out.println("filterA Destroy");
    }
}
