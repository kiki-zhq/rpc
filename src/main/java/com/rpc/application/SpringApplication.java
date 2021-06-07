package com.rpc.application;

import com.rpc.annotation.Autowire;
import com.rpc.annotation.RestController;
import com.rpc.annotation.Service;
import com.rpc.test.Controller;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 主处理控制程序
 * </p>
 *
 * @author kiki
 * @date 2021/6/6
 */
public class SpringApplication {

    /**
     * 反射框架
     */
    private static ReflectionUtils reflectionUtils;


    /**
     * 初始化
     *
     * @param main 主入口类
     * @param args 参数
     * @author zhq
     * @since 2021/6/6 7:54 下午
     */
    public static void run(Class<?> main, String[] args) {
        //初始化反射框架
        reflectionUtils = new ReflectionUtils(main);

        //初始化所有被标记的注解
        initBean();

        Controller controller = (Controller) BeanManager.getBean(Controller.class);
    }


    /**
     * 初始化所有被标记的注解
     *
     * @author zhq
     * @since 2021/6/6 7:56 下午
     */
    private static void initBean() {
        Set<Class<?>> signClass = new HashSet<>();
        //获取标记restController的类
        Set<Class<?>> signRestControllerClass = reflectionUtils.getClassByAnnotation(RestController.class);
        //获取标记service的类
        Set<Class<?>> signServiceClass = reflectionUtils.getClassByAnnotation(Service.class);

        signClass.addAll(signRestControllerClass);
        signClass.addAll(signServiceClass);

        //将所有类注册进去bean管理器
        BeanManager.putBeanList(signClass);
    }
}
