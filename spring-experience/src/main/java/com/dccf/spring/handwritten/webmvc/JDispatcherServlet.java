package com.dccf.spring.handwritten.webmvc;

import com.dccf.beans.JApplicationContext;
import com.dccf.spring.handwritten.anno.JController;
import com.dccf.spring.handwritten.anno.JRequestMapping;
import com.dccf.spring.handwritten.anno.JResource;
import com.dccf.spring.handwritten.webmvc.servlet.*;
import com.dccf.spring.util.CommonUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class JDispatcherServlet extends HttpServlet {

  private static final String CONTEXT_CONFIG_LOCAT = "contextConfigLocation";

  private static List<JHandlerMapping> handlerMappings =
      Collections.synchronizedList(new ArrayList<>());

  private List<String> classNames = new ArrayList<>();

  private Map<String, Object> singleObjMap = new ConcurrentHashMap<>();

  private Map<String, Method> handlerMapping = new ConcurrentHashMap<>();

  private Map<JHandlerMapping, JHandlerAdapter> handlerAdapterMap = new ConcurrentHashMap<>();

  private JViewResolver viewResolver;

  private JHandlerAdapter getHandlerAdapter(JHandlerMapping handlerMap) {
    return !handlerAdapterMap.isEmpty() ? handlerAdapterMap.get(handlerMap) : null;
  }

  private JHandlerMapping getHandlerMapping(HttpServletRequest req) {
    String uri = req.getRequestURI().replaceAll("/+", "/");
    JHandlerMapping map = null;
    for (JHandlerMapping mapping : handlerMappings) {
      if (mapping.getUrl().matcher(uri).matches()) {
        map = mapping;
      }
    }
    return map;
  }

  /**
   * 初始化ViewResolver
   *
   * @param context
   */
  private void initViewResolvers(JApplicationContext context) {
    //    String filePath=CommonUtil.getPropertiesField();
    String contextConfigLocation = context.getContextConfigLocation();
    try {
      ClassLoader loader = this.getClass().getClassLoader();
      String templateRootDir =
          loader
              .getResource(CommonUtil.getPropertiesField(contextConfigLocation, "templateRootDir"))
              .getFile();
      viewResolver = new JViewResolver(templateRootDir);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 初始化HandlerAdapters
   *
   * @param context
   */
  private void initHandlerAdapters(JApplicationContext context) {}

  /**
   * 该方法主要作用就是初始化handlerMapping
   *
   * @param context
   */
  private void initHandlerMappings(JApplicationContext context) {
    if (!CollectionUtils.isEmpty(singleObjMap)) {
      Set<Map.Entry<String, Object>> entrySet = singleObjMap.entrySet();
      for (Map.Entry<String, Object> entry : entrySet) {
        Object bean = entry.getValue();
        Class<?> cls = bean.getClass();
        String baseUrl = "";
        if (cls.isAnnotationPresent(JRequestMapping.class)) {
          JRequestMapping requestMappingAnno = cls.getAnnotation(JRequestMapping.class);
          baseUrl = requestMappingAnno.value();
        }

        //        遍历所有的方法
        for (Method m : cls.getMethods()) {
          if (m.isAnnotationPresent(JRequestMapping.class)) {
            continue;
          }
          JRequestMapping requestMappingAnno = m.getAnnotation(JRequestMapping.class);
          String url = ("/" + baseUrl + "/" + requestMappingAnno.value()).replaceAll("/+", "/");
          JHandlerMapping mapping = new JHandlerMapping(bean, Pattern.compile(url), m);
          handlerMappings.add(mapping);
          System.out.println("url->" + url + ",method->" + m.getName());
        }
      }
    }
  }

  private void initStrategies(JApplicationContext context) {
    initHandlerMappings(context);
    initHandlerAdapters(context);
    initViewResolvers(context);
  }

  /*
    @Override
    public void init(ServletConfig config) throws ServletException {
      try {
        System.out.println("开始初始化JDispatcherServlet=>>>>>>>>>>");
        String contextConfigLocation = config.getInitParameter(CONTEXT_CONFIG_LOCAT);
        doConfig(contextConfigLocation);
        doIoc();
        doDi();
        doHandlerMapping();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  */

  public void init(ServletConfig config) throws ServletException {
    String filePath = config.getInitParameter("contextConfigLocation");
    JApplicationContext context = new JApplicationContext(filePath);
    initStrategies(context);
    //    doHandlerMapping();
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("utf-8");
    resp.setCharacterEncoding("utf-8");
    doDispatch(req, resp);
  }

  private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    /*
    PrintWriter writer = null;
       try {
         String url = req.getRequestURI().replaceAll("/+", "/"); // 将多个/合并为一个/
         resp.setCharacterEncoding("utf-8");
         req.setCharacterEncoding("utf-8");
         writer = resp.getWriter();
         if (!handlerMapping.containsKey(url)) {
           //      表示找不到url对应的方法,返回404
           writer.write("404 Not Found!");
         } else {
           writer.write("Welcome!");
           Method m = handlerMapping.get(url);
           String beanName = CommonUtil.capitalize(m.getDeclaringClass().getSimpleName());
           Object bean = singleObjMap.get(beanName);

           */
    /*
    给controller的方法赋值,就需要参数3作为目标方法,参数4表示目标方法形参列表
     m.invoke()
     */
    /*
        Object params[] = new Object[m.getParameters().length];
        params = getMethodParams(req, resp, m, m.getParameters());
        m.invoke(bean, params);
      }
    } catch (IOException | IllegalAccessException | InvocationTargetException ex) {
      ex.printStackTrace();
    } finally {
      if (writer != null) {
        writer.close();
      }
    }
    */

    try {
      //    根据url获取handlerMapping
      JHandlerMapping handlerMap = getHandlerMapping(req);
      String viewName = "404.html"; //      说明匹配不到uri所以返回404.html
      Map<String, Object> model = null;
      if (handlerMap != null) {
        //      说明匹配到了uri
        JHandlerAdapter adapter = getHandlerAdapter(handlerMap);
        JModelAndView mav = adapter.handler(req, resp, handlerMap); // 先执行目标controller然后才拿到modelAndView
        viewName = mav.getViewName();
        model = mav.getModel();
      }
      JView view = viewResolver.resolverViewName(viewName);
      view.render(req, resp, model);
      return;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void doConfig(String contextConfigLocation) {
    /*
    ClassLoader loader = this.getClass().getClassLoader();
     InputStream is = loader.getResourceAsStream(contextConfigLocation);
     try {
       Properties p = new Properties();
       p.load(is);
       String basePackage = p.getProperty("basePackage");
       URL url = loader.getResource(basePackage.replace(".", "/"));
       File classPath = new File(url.getFile());
       recursionFile(classPath, basePackage);
     } catch (Exception ex) {
       ex.printStackTrace();
     } finally {
       if (is != null) {
         try {
           is.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
       }
     }
     */

    try {
      String basePackage = CommonUtil.getPropertiesField(contextConfigLocation, "basePackage");
      URL url = this.getClass().getClassLoader().getResource(basePackage.replace(".", "/"));
      File classPath = new File(url.getFile());
      recursionFile(classPath, basePackage);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void recursionFile(File classPath, String basePackage) {
    File[] classList = classPath.listFiles();
    for (File file : classList) {
      if (file.isDirectory()) {
        //        说明是一个文件夹,那就继续递归
        recursionFile(file, basePackage);
      } else {
        if (file.getName().endsWith(".class")) {
          String className = basePackage + "." + file.getName().replace(".class", "");
          classNames.add(className);
        }
      }
    }
  }

  /**
   * 1.遍历classNames,通过反射Class.forName方法得到每一个class文件的对象
   * 2.判断class对象里面是否包含这些注解: @JController、@JService、@JRepository、@JComponent
   * 3.如果包含这些注解就将其对象纳入spring容器 4.用beanName作为key,value为实例对象
   */
  private void doIoc() {
    if (!classNames.isEmpty()) {
      try {
        for (String name : classNames) {
          Class<?> cls = Class.forName(name);
          String value = cls.getAnnotation(JController.class).value();
          if (StringUtils.isEmpty(value)) {
            /*
            getSimpleName() 是 Java 中的一个方法，它属于 java.lang.reflect.Name 接口。这个方法用于获取一个类型的简单名称。
             具体来说，对于基本类型，它返回对应的名称（例如，对于 int，它返回 "int"）。对于类和接口，它返回不包括任何包名的名称（例如，对于 java.util.List，它返回 "List"）。
             以下是一些示例：
             System.out.println(int.class.getSimpleName());  // 输出 "int"
             System.out.println(String.class.getSimpleName());  // 输出 "String"
             System.out.println(java.util.List.class.getSimpleName());  // 输出 "List"
             注意：这个方法主要用于反射和某些工具类，例如 org.apache.commons.lang3.reflect.TypeUtils。在常规的 Java 编程中，你可能不会经常使用它。
            */
            value = cls.getSimpleName();
            value = value.substring(0, 1).toLowerCase() + value.substring(1); // 首字母小写
          }
          Object o = cls.newInstance();
          singleObjMap.put(value, o);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  /** 给属性赋值 */
  private void doDi() throws IllegalAccessException {
    if (!CollectionUtils.isEmpty(singleObjMap)) {
      Set<String> set = singleObjMap.keySet();
      Iterator<String> iter = set.iterator();
      while (!iter.hasNext()) {
        String key = iter.next();
        Object val = singleObjMap.get(key);
        for (Field f : val.getClass().getDeclaredFields()) {
          if (!f.isAnnotationPresent(JResource.class)) {
            continue;
          }
          f.setAccessible(true);
          JResource jResourceAnno = f.getAnnotation(JResource.class);
          String beanName = jResourceAnno.value().trim();
          if (!StringUtils.isEmpty(beanName)) {
            beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1); // 首字母小写
          }
          Object o = this.singleObjMap.get(beanName);
          f.set(val, o);
        }
      }
    }
  }

  private void doHandlerMapping() {
    if (!CollectionUtils.isEmpty(singleObjMap)) {
      Set<Map.Entry<String, Object>> entrySet = singleObjMap.entrySet();
      for (Map.Entry<String, Object> entry : entrySet) {
        Class<?> cls = entry.getValue().getClass();
        String baseUrl = "";
        if (cls.isAnnotationPresent(JRequestMapping.class)) {
          JRequestMapping requestMappingAnno = cls.getAnnotation(JRequestMapping.class);
          baseUrl = requestMappingAnno.value();
        }

        //        遍历所有的方法
        for (Method m : cls.getMethods()) {
          if (m.isAnnotationPresent(JRequestMapping.class)) {
            continue;
          }
          JRequestMapping requestMappingAnno = m.getAnnotation(JRequestMapping.class);
          String url = ("/" + baseUrl + "/" + requestMappingAnno.value()).replaceAll("/+", "/");
          handlerMapping.put(url, m);
          System.out.println("url->" + url + ",method->" + m.getName());
        }
      }
    }
  }
}
