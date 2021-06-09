package com.rpc.test.service;

import com.rpc.annotation.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author kiki
 * @date 2021/6/7
 */
@Service
public class ServiceImpl {

    public void say() {
        System.out.println("hello world");
    }
}
