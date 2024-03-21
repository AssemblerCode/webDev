package com.dccf.spring;

import com.dccf.jinwh.Main;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JinwhApplicationContext {

  private Class configClass;

  private ConcurrentHashMap<String, Object> singletonObjMap = new ConcurrentHashMap<>(); // 单例池
  private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap =
      new ConcurrentHashMap<>(); // 存放bean定义相关信息

  private List<? super BeanPostProcessor> beanPostProcessorList =
      Collections.synchronizedList(new ArrayList()); // 这个线程安全的集合是用来存放BeanPostProcessor的实现类或者子类用的

  public JinwhApplicationContext(Class configClass) throws Exception {
    this.configClass = configClass;
    scan(configClass);
    Iterator<String> it = beanDefinitionMap.keys().asIterator();
    while (it.hasNext()) {
      String beanName = it.next();
      BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
      if ("singleton".equals(beanDefinition.getScope())) {
        //        Object bean = this.createBean(beanDefinition); // 单例bean
        //        singletonObjMap.put(beanName,bean);
        Object bean =
            singletonObjMap.containsKey(beanName)
                ? singletonObjMap.get(beanName)
                : this.createBean(beanDefinition);
        singletonObjMap.put(beanName, bean);
      }
    }
  }

  private Object createBean(BeanDefinition beanDefinition) throws Exception {
    Class cls = beanDefinition.getCls();
    Object o = cls.getDeclaredConstructor().newInstance();
    Field[] fields = cls.getDeclaredFields();
    for (Field f : fields) {
      if (f.isAnnotationPresent(Autowired.class)) {
        Object bean = this.getBean(f.getName());
        f.setAccessible(true); // 设置可访问(私有字段也能被访问)
        f.set(o, bean); //  参数1是属性所在的对象,参数2是属性值
      }
    }

    /*
     o  是一个对象，BeanNameAware 表示一个类或接口。o 是 BeanNameAware 类（或接口）的实例或者子类实例时，结果 result 返回 true，否则返回 false。
    编译器会检查 o 能否转换成右边的 BeanNameAware 类型，如果不能转换则直接报错，如果不能确定类型，则通过编译。这句话有些难理解，下面我们举例说明。
    Person p1 = new Person();
    System.out.println(p1 instanceof String);    // 编译报错
    System.out.println(p1 instanceof List);    // false
    System.out.println(p1 instanceof List<?>);    // false
    System.out.println(p1 instanceof List<Person>);    // 编译报错

    上述代码中，Person 的对象 p1 很明显不能转换为 String 对象，那么p1 instanceof String不能通过编译，但p1 instanceof List却能通过编译，而instanceof List<Person>又不能通过编译了。
    关于这些问题，可以查看Java语言规范Java SE8版寻找答案
        */
    String beanName = beanDefinition.getBeanName();
    if (o instanceof BeanNameAware) {
      ((BeanNameAware) o).setBeanName(beanName);
    }

    if (o instanceof ApplicationContextAware) {
      //     这个this就是spring容器本身
      ((ApplicationContextAware) o).setApplicationContext(this);
    }

    //    执行bean的初始化之前的方法,无法保证那个实现类的postProcessBeforeInitialization方法先执行
    for (int i = 0; i < beanPostProcessorList.size(); i++) {
      BeanPostProcessor bpp = (BeanPostProcessor) beanPostProcessorList.get(i);
      o = bpp.postProcessBeforeInitialization(o, beanName);
    }

    //    执行bean的初始化方法
    if (o instanceof InitiallizingBean) {
      ((InitiallizingBean) o).afterPropertiesSet();
    }

    //    执行bean的初始化之后的方法,无法保证那个实现类的postProcessAfterInitialization方法先执行
    for (int i = 0; i < beanPostProcessorList.size(); i++) {
      BeanPostProcessor bpp = (BeanPostProcessor) beanPostProcessorList.get(i);
      o = bpp.postProcessAfterInitialization(o, beanName);
    }

    if (cls.isAnnotationPresent(Transaction.class)) {
      // 使用cglib来代理
      Enhancer enhancer = new Enhancer();
      enhancer.setSuperclass(cls); // 设置父类
      Object target = o; // target表示目标对象
      enhancer.setCallback(
          //      设置对被代理(目标)方法的拦截
          new MethodInterceptor() {

            /**
             * @param o 目标对象
             * @param method 目标方法
             * @param objects 目标方法参数
             * @param methodProxy
             * @return 业务方法的返回值
             * @throws Throwable
             */
            @Override
            public Object intercept(
                Object o, Method method, Object[] objects, MethodProxy methodProxy)
                throws Throwable {
              Object invoke = null;
              try {
                System.out.println("开启事务");
                System.out.println("执行目标方法");
                invoke = method.invoke(target, objects);
                System.out.println("提交事务");
              } catch (Exception ex) {
                System.out.println("这里执行回滚事务操作");
              }
              return invoke;
            }
          });
      o = enhancer.create(); // 拿到代理对象
    }

    return o;
  }

  private void scan(Class configClass)
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
          InvocationTargetException, InstantiationException {
    //      解析配置类
    //        拿到ComponentScan注解的value属性
    ComponentScan componentScanAnno =
        (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
    String scanPath = componentScanAnno.value();
    System.out.println("scanPath=>" + scanPath);

    //  通过类加载器去获取扫描路径下的的所有class文件
    //    这个类加载器是去target目录下面去获取资源的,所以要把.替换为/
    ClassLoader loader = Main.class.getClassLoader();
    URL resource = loader.getResource(scanPath.replace(".", "/"));
    File file = new File(resource.getFile());
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (File f : files) {
        // 获取文件的全路径名称
        String fileAbsolutePath = f.getAbsolutePath();

        // 表示要拿到以.class结尾的文件
        if (fileAbsolutePath.endsWith(".class")) {

          // 拿到所有文件后就截取以com开头和以.class结尾的文件名称,并将\替换为. ,如此一来就可以拿到全限定名了
          System.out.println("fileAbsolutePath=>" + fileAbsolutePath);
          String className =
              fileAbsolutePath
                  .substring(fileAbsolutePath.indexOf("com"), fileAbsolutePath.indexOf(".class"))
                  .replace("\\", ".");

          //        加载className
          Class<?> cls = loader.loadClass(className);
          //          判断是否包含Component注解,如果包含就通过反射创建对象然后纳入spring
          if (cls.isAnnotationPresent(Component.class)) {
            Component componentAnno = cls.getDeclaredAnnotation(Component.class);
            String beanName = componentAnno.value();

            //           判断你这个类是否实现了BeanPostProcessor接口
            if (BeanPostProcessor.class.isAssignableFrom(cls)) {
              beanPostProcessorList.add(
                  (BeanPostProcessor) cls.getDeclaredConstructor().newInstance());
            }

            //            判断是否单例还是多例
            BeanDefinition beanDef = new BeanDefinition();
            beanDef.setCls(cls);
            beanDef.setBeanName(beanName);
            //            beanDef.setScope("singleton"); // 默认单例
            if (cls.isAnnotationPresent(Scope.class)) {
              Scope scopeAnno = cls.getDeclaredAnnotation(Scope.class);
              beanDef.setScope(scopeAnno.value());
            }
            beanDefinitionMap.put(beanName, beanDef);
          }
        }
      }
    }
  }

  public Object getBean(Class type) throws Exception {
    Iterator<Map.Entry<String, BeanDefinition>> it = beanDefinitionMap.entrySet().iterator();
    Object result = null;
    while (it.hasNext()) {
      Map.Entry<String, BeanDefinition> next = it.next();
      String key = next.getKey();
      BeanDefinition value = next.getValue();
      if (value.getCls().equals(type)) {
        result = getBean(key);
        break;
      }
    }
    return result;
  }

  public Object getBean(String beanName) throws Exception {
    if (beanDefinitionMap.containsKey(beanName)) {
      BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
      return "singleton".equals(beanDefinition.getScope())
          ? singletonObjMap.get(beanName)
          : this.createBean(beanDefinition);
    }
    return null;
  }
}
