package com.rpc.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 请求路径
 * </p>
 *
 * @author zhq
 * @date 2021/6/6
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    /**
     * 路径
     */
    String value() default "";

    /**
     * 方式
     */
    String method() default "";


}
