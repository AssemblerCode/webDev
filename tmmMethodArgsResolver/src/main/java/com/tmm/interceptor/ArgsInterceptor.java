package com.tmm.interceptor;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class ArgsInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前执行的逻辑
        System.out.println("Pre Handle method is Calling");

        String header = request.getHeader(HttpHeaders.CONTENT_TYPE);
        String jsonStr = readRequestBody(request);
        Map<String, Object> map = objectMapper.readValue(jsonStr, Map.class);
        if ("application/json".equalsIgnoreCase(header) & StrUtil.isNotBlank(jsonStr)) {
            HandlerMethod method = (HandlerMethod) handler;
            List<MethodParameter> paramList = Arrays.asList(method.getMethodParameters());
            for (MethodParameter p : paramList) {
                if (p.getParameterAnnotation(RequestAttribute.class) != null) {
                    String key = p.getParameterAnnotation(RequestAttribute.class).name();
                    Object value = map.get(key);
                    System.out.println("key:" + key + " value:" + value);
                    request.setAttribute(key, value);
                }
            }
            String key = "";
        }
        return true; // 返回 true 表示继续处理请求，返回 false 表示中断请求处理
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后，视图渲染之前执行的逻辑
        System.out.println("Post Handle method is Calling");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在整个请求完成之后执行的逻辑
        System.out.println("Request and Response is completed");
    }

    private String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
