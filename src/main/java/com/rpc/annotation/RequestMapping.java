package com.rpc.annotation;

import com.rpc.enums.RequestMethodEnum;

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
    RequestMethodEnum method() default RequestMethodEnum.ALL;


}
