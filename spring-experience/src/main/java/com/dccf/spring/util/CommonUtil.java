package com.dccf.spring.util;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonUtil {

  public static String getPropertiesField(String file, String key) throws IOException {
    Properties p = new Properties();
    InputStream is = null;
    is = CommonUtil.class.getClassLoader().getResourceAsStream(file);
    p.load(is );
    String value = p.getProperty(key,"");
    is.close();
    return value;
  }

  /**
   * 将String转为八大包装类
   *
   * @param value string
   * @param pamasType 目标包装类
   * @return
   */
  public static Object string20ther(String value, Class<?> pamasType) {
    Object obj = null;
    if (StringUtils.isEmpty(value)) {
      return obj;
    }
    if (pamasType == String.class) {
      obj = value;
    } else if (pamasType == Integer.class) {
      obj = Integer.parseInt(value);
    } else if (pamasType == Double.class) {
      obj = Double.parseDouble(value);
    } else if (pamasType == Long.class) {
      obj = Long.parseLong(value);
    } else if (pamasType == Byte.class) {
      obj = Byte.parseByte(value);
    } else if (pamasType == Short.class) {
      obj = Short.parseShort(value);
    } else if (pamasType == Character.class) {
      obj = value.charAt(0);
    } else if (pamasType == Boolean.class) {
      obj = Boolean.parseBoolean(value);
    }
    return obj;
  }

  /**
   * 将beanName的首字母小写
   *
   * @param str
   * @return
   */
  public static String capitalize(String str) {
    return str == null | "".equals(str.trim())
        ? str
        : Character.toUpperCase(str.charAt(0)) + str.substring(1);
  }
}
