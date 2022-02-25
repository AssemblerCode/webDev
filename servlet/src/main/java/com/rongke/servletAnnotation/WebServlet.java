package com.rongke.servletAnnotation;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@javax.servlet.annotation.WebServlet(value = {"/webServ", "/**"})
public class WebServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String encode = "utf-8";
        req.setCharacterEncoding(encode);

        resp.setContentType("text/html;charset=utf-8");

        resp.setCharacterEncoding(encode);
        resp.setHeader("Content-Type", "text/html;charset=utf-8");
        resp.setHeader("Connection", "keep-alive");
        resp.setHeader("Content-Length", encode.length() + "");
        resp.setHeader("Date", new Date().toString());
        resp.setHeader("Server", "Apache");
        String htmlCode = "<html>" + "<head>" +
                "</head>" +
                "<body>" +
                "<p>" +
                "HelloWorld" +
                "</p>" +
                "</body>" +
                "</html>";
        PrintWriter writer = resp.getWriter();
        writer.println(htmlCode);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    }
}
