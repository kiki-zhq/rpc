package com.rpc.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author kiki
 * @date 2021/6/16
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcReference {
}
