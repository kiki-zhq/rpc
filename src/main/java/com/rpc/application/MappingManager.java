package com.rpc.application;

import com.rpc.enums.RequestMethodEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 路径管理器
 * </p>
 *
 * @author kiki
 * @date 2021/6/7
 */
public class MappingManager {

    /**
     * 类路径管理map key-> 路径 value->类
     */
    private static Map<String, Class<?>> classMappingMap = new HashMap<>();

    /**
     * 方法路径管理map key-> 路径 value-> 方法
     */
    private static Map<String, String> methodMappingMap = new HashMap<>();

    /**
     * 路径请求方式管理map key-> 路径 value-> 方式
     */
    private static Map<String, RequestMethodEnum> requestMethodEnumMap = new HashMap<>();
}
