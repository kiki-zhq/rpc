package com.rpc;

import com.rpc.application.SpringApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author zhq
 * @date 2021/6/6
 */
public class Test {

    /**
     * 端口号
     */
    private static final int PORT = 8080;

    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) {
        SpringApplication.run(Test.class, args, PORT);
    }

}
