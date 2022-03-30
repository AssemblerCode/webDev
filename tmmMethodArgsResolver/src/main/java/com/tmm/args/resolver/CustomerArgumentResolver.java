package com.tmm.args.resolver;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.hasMethodAnnotation(CustomRequestParam.class);
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String header = request.getHeader(HttpHeaders.CONTENT_TYPE);
        String jsonStr = readRequestBody(request);
        Map<String, Object> resultMap = new HashMap<>();
        Method method = parameter.getMethod();
        List<Object> argList = Arrays.asList(new Object[method.getParameterCount()]);
        if ("application/json".equalsIgnoreCase(header) & StrUtil.isNotBlank(jsonStr)) {
            Map<String, Object> map = objectMapper.readValue(jsonStr, Map.class);
            if (CollUtil.isNotEmpty(argList)) {
                String key = "";
                for (int i = 0; i < method.getParameterCount(); i++) {
                    MethodParameter currentParam = new MethodParameter(method, i);
                    if (currentParam.hasParameterAnnotation(RequestAttribute.class)) {
                        key = currentParam.getParameter().getName();
                        Object value = map.get(key);
                        resultMap.put(key, value);
                        request.setAttribute(key, value);
                    }
                }
                return resultMap;
//                return resultMap;
            }
        }
        return webRequest.getParameter(parameter.getParameterName());

//        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
//        String json = readRequestBody(request);; // 假设JSON数据被放在名为"json"的请求参数中
//        String paramName = "";
//        Map<String, Object> params = new HashMap<>();
//        if (StrUtil.isNotBlank(json)) {
//            params = objectMapper.readValue(json, HashMap.class);
//            paramName = parameter.getParameterName();
//            mavContainer.addAttribute(paramName, params.get(paramName));
////            return webRequest.getParameter( paramName);
//        }
//
//        return webRequest.getParameter(parameter.getParameterName());
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
