package com.rpc.producer;

import com.rpc.application.SpringApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public class RpcProducerApplication {

    /**
     * 端口号
     */
    private static final int PORT = 8082;

    /**
     * rpc端口
     */
    private static final int rpcPort = 8081;

    public static void main(String[] args) {
        SpringApplication.run(RpcProducerApplication.class, args, PORT, rpcPort);
    }
}
