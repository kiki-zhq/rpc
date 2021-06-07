package com.rpc.enums;

/**
 * <p>
 * 请求枚举
 * </p>
 *
 * @author kiki
 * @date 2021/6/7
 */
public enum RequestMethodEnum {

    /**
     * 获取
     */
    GET("GET"),

    /**
     * 添加
     */
    POST("POST"),

    /**
     * 更新
     */
    PUT("PUT"),

    /**
     * 删除
     */
    DELETE("DELETE");

    public String value;

    RequestMethodEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
