package com.rpc.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author kiki
 * @date 2021/6/6
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpringApplication {

    /**
     * 存放路径值
     */
    String value() default "";
}
