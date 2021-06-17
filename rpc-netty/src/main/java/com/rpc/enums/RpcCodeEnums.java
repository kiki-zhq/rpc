package com.rpc.enums;

/**
 * <p>
 * Rpc状态码
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public enum RpcCodeEnums {

    /**
     * 成功
     */
    OK(200),

    /**
     * 错误
     */
    ERROR(500),
    ;


    private Integer value;

    RpcCodeEnums(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
