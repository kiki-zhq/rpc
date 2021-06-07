package com.rpc.application;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * <p>
 * 反射类工具
 * </p>
 *
 * @author kiki
 * @date 2021/6/6
 */
public class ReflectionUtils {

    private Class<?> type;

    private Reflections reflections;

    public ReflectionUtils(Class<?> type) {
        this.type = type;
        this.reflections = new Reflections(type.getPackage());
    }

    /**
     * 获取标记类
     *
     * @param annotation 注解
     * @return 被注解标记类
     * @author zhq
     * @since 2021/6/6 7:47 下午
     */
    public Set<Class<?>> getClassByAnnotation(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation);
    }

    /**
     * 获取标记方法
     *
     * @param annotation 注解
     * @return 被标记的方法
     * @author zhq
     * @since 2021/6/7 10:40 上午
     */
    public Set<Method> getMethodByAnnotation(Class<? extends Annotation> annotation) {
        return reflections.getMethodsAnnotatedWith(annotation);
    }

    /**
     * 获取标记属性值
     *
     * @param annotation 注解
     * @return 被标记的属性值
     * @author zhq
     * @since 2021/6/7 10:59 上午
     */
    public Set<Field> getFieldByAnnotation(Class<? extends Annotation> annotation) {
        return reflections.getFieldsAnnotatedWith(annotation);
    }
}
