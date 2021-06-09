package com.rpc.application.mvc;

import com.rpc.annotation.RequestMapping;
import com.rpc.application.SpringApplication;
import com.rpc.enums.RequestMethodEnum;

import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>
 * 路径管理器 一个类只允许一个路径
 * </p>
 *
 * @author kiki
 * @date 2021/6/7
 */
public class Handler {

    /**
     * 类路径管理map key-> 路径 value->类
     */
    private static Map<String, Class<?>> classMappingMap = new HashMap<>();

    /**
     * 方法路径管理map key-> 路径 value-> 方法列表
     */
    private static Map<String, Set<String>> methodMappingMap = new HashMap<>();

    /**
     * 路径请求方式管理map key->方式  value-> 路径列表
     */
    private static Map<RequestMethodEnum, Set<String>> requestMethodEnumMap = new HashMap<>();

    /**
     * 生成类路径管理
     *
     * @author zhq
     * @since 2021/6/7 12:14 下午
     */
    public static void initMapping() {
        //设置类路径管理
        setClassMapping();
        //设置方法路径管理
        setMethodMapping();
    }

    /**
     * 设置方法管理
     *
     * @author zhq
     * @since 2021/6/7 12:06 下午
     */
    private static void setMethodMapping() {
        Set<Method> methodSet = SpringApplication.getReflectionUtils().getMethodByAnnotation(RequestMapping.class);
        for (Method method : methodSet) {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            //设置方法路径管理
            Set<String> methodNameSet = methodMappingMap.getOrDefault(requestMapping.value(), new HashSet<>());
            methodNameSet.add(method.getName());
            methodMappingMap.put(requestMapping.value(), methodNameSet);
            //设置路径请求方式管理
            Set<String> urls = requestMethodEnumMap.getOrDefault(requestMapping.method(), new HashSet<>());
            urls.add(requestMapping.value());
            requestMethodEnumMap.put(requestMapping.method(), urls);
        }
    }

    /**
     * 设置类路径管理
     *
     * @author zhq
     * @since 2021/6/7 12:07 下午
     */
    private static void setClassMapping() {
        Set<Class<?>> classSet = SpringApplication.getReflectionUtils().getClassByAnnotation(RequestMapping.class);

        for (Class<?> classMapping : classSet) {
            RequestMapping requestMapping = classMapping.getAnnotation(RequestMapping.class);
            //设置类路径管理
            classMappingMap.put(requestMapping.value(), classMapping);
            //设置路径请求方式管理
            Set<String> urls = requestMethodEnumMap.getOrDefault(requestMapping.method(), new HashSet<>());
            urls.add(requestMapping.value());
            requestMethodEnumMap.put(requestMapping.method(), urls);
        }
    }

    /**
     * 获取类路径管理
     *
     * @return 类路径管理
     * @author zhq
     * @since 2021/6/7 12:21 下午
     */
    public static Map<String, Class<?>> getClassMappingMap() {
        return classMappingMap;
    }

    /**
     * 获取方法路径管理
     *
     * @return 方法路径管理
     * @author zhq
     * @since 2021/6/7 12:21 下午
     */
    public static Map<String, Set<String>> getMethodMappingMap() {
        return methodMappingMap;
    }

    /**
     * 获取请求方法路径管理
     *
     * @return 请求方法路径管理
     * @author zhq
     * @since 2021/6/7 12:22 下午
     */
    public static Map<RequestMethodEnum, Set<String>> getRequestMethodEnumMap() {
        return requestMethodEnumMap;
    }
}
