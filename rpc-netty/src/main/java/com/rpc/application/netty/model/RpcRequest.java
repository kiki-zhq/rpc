package com.rpc.application.netty.model;

import lombok.Getter;
import lombok.Setter;


/**
 * <p>
 * Rpc请求
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
@Getter
@Setter
public class RpcRequest {

    /**
     * 唯一id(作为唯一标识)
     */
    private String unionId;

    /**
     * 类名称
     */
    private Class<?> className;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数类型
     */
    private Class<?>[] parameterType;

    /**
     * 参数
     */
    private Object[] parameterValue;
}
