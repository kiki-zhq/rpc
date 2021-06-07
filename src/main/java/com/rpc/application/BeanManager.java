package com.rpc.application;

import com.rpc.annotation.Autowire;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * Bean管理器
 * </p>
 *
 * @author kiki
 * @date 2021/6/6
 */
public class BeanManager {

    /**
     * 注册beanMap
     */
    private static Map<String, Object> beanMap = new HashMap<>();


    /**
     * 注册Bean列表
     *
     * @param signClass 标记类列表
     * @author zhq
     * @since 2021/6/6 8:11 下午
     */
    public static void putBeanList(Set<Class<?>> signClass) {
        for (Class<?> sign : signClass) {
            try {
                Object bean = beanMap.getOrDefault(sign.getName(), sign.newInstance());
                //获取被标记的autowire注解的属性值 获取DeclareField防止值私有化
                List<Field> fieldList = Arrays.stream(sign.getDeclaredFields())
                        .filter(a -> a.getAnnotationsByType(Autowire.class).length > 0).collect(Collectors.toList());
                //设置属性的依赖
                setFieldBean(fieldList, bean);

                beanMap.put(sign.getName(), bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取bean实例
     *
     * @param signClass 标记类
     * @return 实例
     * @author zhq
     * @since 2021/6/6 8:19 下午
     */
    public static Object getBean(Class<?> signClass) {
        return beanMap.get(signClass.getName());
    }

    /**
     * 设置属性的依赖
     *
     * @param fields 属性值列表
     * @param bean   bean
     * @author zhq
     * @since 2021/6/7 11:09 上午
     */
    private static void setFieldBean(List<Field> fields, Object bean) throws InstantiationException, IllegalAccessException {
        for (Field field : fields) {
            field.setAccessible(true);
            if (beanMap.get(field.getType().getName()) == null) {
                beanMap.put(field.getType().getName(), field.getType().newInstance());
            }
            Object fieldBean = beanMap.get(field.getType().getName());
            field.set(bean, fieldBean);
        }
    }

    /**
     * 注册注解列表
     *
     * @param signAutoWireField 标记autowire属性
     * @author zhq
     * @since 2021/6/7 11:07 上午
     */
    public static void putSignAutoWireList(Set<Field> signAutoWireField) {
        for (Field field : signAutoWireField) {
            try {
                beanMap.put(field.getType().getName(), field.getType().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}