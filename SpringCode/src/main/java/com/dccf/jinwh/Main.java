package com.dccf.jinwh;

import com.dccf.service.UserService;
import com.dccf.spring.JinwhApplicationContext;
import com.dccf.spring.UserInterface;

import java.lang.reflect.InvocationTargetException;

public class Main {

  public static void main(String[] args)
      throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
          IllegalAccessException, InvocationTargetException, Exception {
    JinwhApplicationContext ctx = null;
    ctx = new JinwhApplicationContext(AppConfig.class);
    UserInterface userService = (UserInterface) ctx.getBean("userService");
    userService.test();
  }
}
