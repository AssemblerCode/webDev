package com.dccf.spring.handwritten.webmvc.servlet;

import com.dccf.spring.handwritten.anno.JRequestParam;
import com.dccf.spring.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JHandlerAdapter {

  /**
   * 获取controller的实参列表
   *
   * @param req HttpServletRequest对象
   * @param resp HttpServletResponse对象
   * @param m 目标方法
   * @param params 目标方法的形参列表
   * @return
   */
  private Object[] getMethodParams(
      HttpServletRequest req, HttpServletResponse resp, Method m, Object params[]) {
    Map<String, Integer> paramIndexMap =
        new HashMap<>(); // key表示JRequestParam注解的value值,value表示使用了JRequestParam注解的参数所在下标

    /*
    为什么返回的是一个二维数组呢，这是因为每一个参数前面可能会有多个不同注解修饰。其中annotations[0]代表第一个参数，annotations[1]代表第二个参数。
     */
    Annotation[][] paramAnnos = m.getParameterAnnotations();
    for (int item = 0; item < paramAnnos.length; item++) {
      System.out.println("第一维度表示参数=>" + paramAnnos[item]);
      for (Annotation anno : paramAnnos[item]) {
        if (anno instanceof JRequestParam) {
          String paramName = ((JRequestParam) anno).value(); // 获取参数名称
          paramIndexMap.put(paramName, item); // 将注解值与下标映射
        }
      }
    }

    /*
     * 获取一下参数列表里面的所有类型，然后找到 HttpServletRequest和 HttpServletResponse类型的参数并且赋值。
     */
    Class<?>[] paramTypes = m.getParameterTypes();
    for (int item = 0; item < paramTypes.length; item++) {
      Class<?> paramType = paramTypes[item];
      if (paramType == HttpServletRequest.class) {
        params[item] = req;
      }
      if (paramType == HttpServletResponse.class) {
        params[item] = resp;
      }
    }

    Map<String, String[]> reqMap = req.getParameterMap();
    for (String key : reqMap.keySet()) {
      //      reqMap.get(key)返回的是一个数组,需要把数组转为String并且去除方括号
      String value = Arrays.toString(reqMap.get(key));
      value = Arrays.toString(reqMap.get(key)).substring(1, value.length() - 1);
      System.out.println("value=>" + value);
      if (paramIndexMap.containsKey(key)) {
        Integer idx = paramIndexMap.get(key);
        params[idx] = value;
        params[idx] = CommonUtil.string20ther(value, paramTypes[idx]);
      }
    }

    return params;
  }

  /**
   * 1.通过handlerMapping拿到目标方法和目标controller对象和目标方法的形参列表长度
   * 2.通过getMethodParams方法获取controller的实参列表
   * 3.通过反射调用目标方法并且传入实参列表
   *
   * @param request
   * @param response
   * @param handlerMapping
   * @return
   * @throws Exception
   */
  public JModelAndView handler(
      HttpServletRequest request, HttpServletResponse response, JHandlerMapping handlerMapping)
      throws Exception {
    Method m = handlerMapping.getMethod(); // 拿到目标方法
    Object ctrl = handlerMapping.getController(); // 拿到目标controller对象
    Object params[] = new Object[m.getParameterTypes().length]; // 拿到目标方法的形参列表长度
    params = getMethodParams(request, response, m, params); // 获取controller的形参列表
    Object invoke = m.invoke(ctrl, params); // 通过反射执行,并且拿到controller目标方法的返回值也就是modelAndView

//    判断这个controller目标方法的返回值类型是不是ModelAndView,如果是就强转反之则返回null
    return handlerMapping.getMethod().getReturnType() == JModelAndView.class
        ? (JModelAndView)  invoke
        : null;
  }
}
