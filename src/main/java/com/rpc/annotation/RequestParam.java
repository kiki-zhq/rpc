package com.rpc.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author kiki
 * @date 2021/6/9
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {

    /**
     * 路径
     */
    String value() default "";
}
