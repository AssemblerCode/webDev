package com.rongke.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpCookie;

@javax.servlet.annotation.WebServlet(value = {"/getCookie", "/**"})
public class MyCookie extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookies[] = req.getCookies();
        if(cookies.length>0){

            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                String key = c.getName();
                String value=c.getValue();
            }
        }

        Cookie cookie = new Cookie("jinwh","age");
        cookie.setMaxAge(24*60*60);
        cookie.setPath("/getCookie/abc");
        resp.addCookie(cookie);
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        String str = "<html>" +
                "<head>" +
                "</head>" +
                "<body>" +
                "<b>" +
                "学习cookie" +
                "</b>" +
                "</body>" +
                "</html>";
        writer.write(str);
    }
}
