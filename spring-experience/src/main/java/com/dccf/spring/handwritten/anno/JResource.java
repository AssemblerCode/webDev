package com.dccf.spring.handwritten.anno;

import java.lang.annotation.*;

/**
 * @Retention
 * 1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
 * 2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
 * 3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
 *
 * @Target
 *
 * @Target注解用于指定注解可以应用的程序元素类型，它有一个ElementType枚举类型的参数，可以取值为：
 *
 * ElementType.TYPE：可以用于类、接口和枚举类型。
 *
 * ElementType.FIELD：可以用于字段（包括枚举常量）。
 *
 * ElementType.METHOD：可以用于方法。
 *
 * ElementType.PARAMETER：可以用于方法的参数。
 *
 * ElementType.CONSTRUCTOR：可以用于构造函数。
 *
 * ElementType.LOCAL_VARIABLE：可以用于局部变量。
 *
 * ElementType.ANNOTATION_TYPE：可以用于注解类型。
 *
 * ElementType.PACKAGE：可以用于包。
 *
 * ElementType.TYPE_PARAMETER：可以用于类型参数声明（Java 8新增）。
 *
 * ElementType.TYPE_USE：可以用于使用类型的任何语句中（Java 8新增）。
 *
 * @Documented注解表示是否在生成的文档中展示该注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD})
@Documented
public @interface JResource {
  String value() default "";
}
