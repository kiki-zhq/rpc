package com.rpc.application;

import com.rpc.annotation.Autowire;
import com.rpc.annotation.RestController;
import com.rpc.annotation.Service;
import com.rpc.exception.BeanErrorException;
import com.rpc.utils.CheckUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * Bean管理器  TODO 需要解决循环依赖问题
 * </p>
 *
 * @author kiki
 * @date 2021/6/6
 */
public class ApplicationContext {

    /**
     * 注册beanMap key-> name value->实例
     */
    private static Map<String, Object> beanInitMap = new HashMap<>();

    /**
     * 注册beanFactory key-> name  value->map
     */
    private static Map<String, Object> beanInstanceMap = new HashMap<>();

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
                Object bean = beanInitMap.getOrDefault(sign.getName(), sign.newInstance());
                beanInitMap.put(sign.getName(), bean);
                //获取被标记的autowire注解的属性值 获取DeclareField防止值私有化
                List<Field> fieldList = getAnnotationFieldList(sign);
                //设置属性的依赖
                setFieldBean(fieldList, bean, new HashSet<>());


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
        return beanInstanceMap.get(signClass.getName());
    }

    /**
     * 设置属性的依赖 需要递归进行解决循环依赖的问题
     *
     * @param fields 属性值列表
     * @param bean   bean
     * @author zhq
     * @since 2021/6/7 11:09 上午
     */
    private static void setFieldBean(List<Field> fields, Object bean, Set<String> beanName) throws InstantiationException, IllegalAccessException, BeanErrorException {
        if (beanInstanceMap.containsKey(bean.getClass().getName())) {
            return;
        }
        for (Field field : fields) {
            if (beanName.contains(field.getType().getName())) {
                throw new BeanErrorException("bean之间循环依赖了");
            }
            field.setAccessible(true);
            beanName.add(field.getType().getName());
            //子属性bean初始化
            Object subBeanInit;
            if (beanInitMap.get(field.getType().getName()) == null) {
                subBeanInit = field.getType().newInstance();
                beanInitMap.put(field.getType().getName(), subBeanInit);
            } else {
                subBeanInit = beanInitMap.get(field.getType().getName());
            }
            //获取被标记的autowire注解的属性值 获取DeclareField防止值私有化
            List<Field> subfields = getAnnotationFieldList(field.getType());
            if (CheckUtils.isListEmpty(subfields)) {
                //设置bean的属性
                beanInstanceMap.put(bean.getClass().getName(), bean);
            }
            //递归操作
            setFieldBean(subfields, subBeanInit, beanName);
            //设置子属性
            field.set(bean, subBeanInit);
            //设置主bean的属性
            beanInstanceMap.put(bean.getClass().getName(), bean);
        }
    }

    /**
     * 获取被标记的属性
     *
     * @param sign 类
     * @return 被标记的属性
     * @author kiki
     * @since 2021/6/16 5:44 下午
     */
    private static List<Field> getAnnotationFieldList(Class<?> sign) {
        //获取被标记的autowire注解的属性值 获取DeclareField防止值私有化
        return Arrays.stream(sign.getDeclaredFields())
                .filter(a -> a.getAnnotationsByType(Autowire.class).length > 0).collect(Collectors.toList());
    }

    /**
     * 初始化所有被标记的注解
     *
     * @author zhq
     * @since 2021/6/6 7:56 下午
     */
    public static void initBean() {
        Set<Class<?>> signClass = new HashSet<>();
        //获取标记restController的类
        Set<Class<?>> signRestControllerClass = SpringApplication.getReflectionUtils().getClassByAnnotation(RestController.class);
        //获取标记service的类
        Set<Class<?>> signServiceClass = SpringApplication.getReflectionUtils().getClassByAnnotation(Service.class);

        signClass.addAll(signRestControllerClass);
        signClass.addAll(signServiceClass);

        //将所有类注册进去bean管理器
        putBeanList(signClass);
    }
}
