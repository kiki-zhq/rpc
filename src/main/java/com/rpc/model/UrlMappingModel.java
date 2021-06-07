package com.rpc.model;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 地址映射模型
 * </p>
 *
 * @author kiki
 * @date 2021/6/7
 */
@Getter
@Setter
public class UrlMappingModel {

    /**
     * 目标类
     */
    private Class<?> targetClass;

    /**
     * 方法名称
     */
    private String methodName;
}
