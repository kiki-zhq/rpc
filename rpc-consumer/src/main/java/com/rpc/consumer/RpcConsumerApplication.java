package com.rpc.consumer;

import com.rpc.application.SpringApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public class RpcConsumerApplication {

    /**
     * 端口号
     */
    private static final int PORT = 8080;

    /**
     * 地址
     */
    private static final String HOST = "127.0.0.1";

    /**
     * 目标端口
     */
    private static final int TARGET_PORT = 8081;

    public static void main(String[] args) {
        SpringApplication.run(RpcConsumerApplication.class, args, PORT, HOST, TARGET_PORT);
    }

}
