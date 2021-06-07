package com.rpc.application;


import com.rpc.enums.RequestMethodEnum;

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

        //初始化所有被标记的类
        ApplicationContext.initBean();
        //初始化路径
        Handler.initMapping();

        //执行适配器
        HandlerAdapter.handle(null, "http://127.0.0.1:8080/test/get", RequestMethodEnum.GET.getValue());
    }


    /**
     * 获取反射类工具
     *
     * @return 反射类工具
     * @author zhq
     * @since 2021/6/7 12:20 下午
     */
    public static ReflectionUtils getReflectionUtils() {
        return reflectionUtils;
    }

}
