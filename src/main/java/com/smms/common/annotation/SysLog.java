package com.smms.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 */
//定义注解的作用目标：方法
@Target(ElementType.METHOD)
//定义注解的保留策略，RUMTIME注解会在class字节码文件中存在，运行时可通过反射获取
@Retention(RetentionPolicy.RUNTIME)
//说明该注解被包含在javadoc中
@Documented
public @interface SysLog {

    String value() default "";

}
