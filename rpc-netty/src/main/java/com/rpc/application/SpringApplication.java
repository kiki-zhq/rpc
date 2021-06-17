package com.rpc.application;


import com.rpc.application.mvc.Handler;
import com.rpc.application.netty.Client;
import com.rpc.application.netty.Server;
import com.rpc.application.reflection.ReflectionUtils;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
     * 加载日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SpringApplication.class);

    /**
     * 反射框架
     */
    private static ReflectionUtils reflectionUtils;

    /**
     * 线程池
     */
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(2);

    /**
     * 初始化标记以及路径
     *
     * @param main 主入口类
     * @param args 参数
     * @author kiki
     * @since 2021/6/17 6:01 下午
     */
    public static void init(Class<?> main, String[] args) {
        //初始化反射框架
        reflectionUtils = new ReflectionUtils(main);

        //初始化所有被标记的类
        ApplicationContext.initBean();
        System.out.println("初始化所有被标记的类成功");
        //初始化路径
        Handler.initMapping();
        System.out.println("初始化路径成功");
    }

    /**
     * 服务端初始化
     *
     * @param main 主入口类
     * @param args 参数
     * @param port 端口
     * @author zhq
     * @since 2021/6/6 7:54 下午
     */
    public static void run(Class<?> main, String[] args, int port) {
        //初始化标记以及路径
        init(main, args);
        //服务开启
        THREAD_POOL.submit(new Server(port));
    }

    /**
     * 客户端初始化
     *
     * @param main       主入口类
     * @param args       参数
     * @param host       地址
     * @param targetPort 目标端口
     * @author kiki
     * @since 2021/6/17 3:51 下午
     */
    public static void run(Class<?> main, String[] args, String host, int targetPort) {
        //初始化标记以及路径
        init(main, args);
        new Client(host, targetPort).run();
        System.out.println(Client.channel);
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
