package com.tmm.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

//@Aspect
@Component
public class ControllerAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

//    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
//    public void beforeControllerMethod(JoinPoint joinPoint) throws IOException {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        Object tid = request.getAttribute("tid");
//        Object sid = request.getAttribute("sid");
//
//    }

}
