package com.rpc.enums;

import java.util.Arrays;
import java.util.Optional;

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
     * 全部
     */
    ALL(null),

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

    /**
     * 获取请求方法枚举
     *
     * @param method 请求方法
     * @return 方法枚举
     * @author zhq
     * @since 2021/6/7 3:43 下午
     */
    public static RequestMethodEnum getMethodEnum(String method) {
        Optional<RequestMethodEnum> methodEnum = Arrays.stream(RequestMethodEnum.values())
                .filter(a -> method.equals(a.getValue())).findFirst();
        return methodEnum.orElse(RequestMethodEnum.ALL);
    }
}
