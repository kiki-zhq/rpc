package com.rpc.application.netty.model;

import com.rpc.enums.RpcCodeEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * Rpc响应
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
@Getter
@Setter
@NoArgsConstructor
public class RpcResponse {

    /**
     * 唯一id(作为唯一标识)
     */
    private String unionId;

    /**
     * id(作为返回结果)
     */
    private Integer code = RpcCodeEnums.OK.getValue();

    /**
     * 返回类型
     */
    private Class<?> returnType;

    /**
     * 返回类型
     */
    private Object returnValue;

    public RpcResponse(Class<?> returnType, Object returnValue) {
        this.returnType = returnType;
        this.returnValue = returnValue;
    }
}
