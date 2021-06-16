package com.rpc.test.service;

import com.rpc.annotation.Autowire;
import com.rpc.annotation.Service;
import com.rpc.test.TestController;

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

    @Autowire
    private TestServiceImpl testService;

    public void say() {
        System.out.println("hello world");
        testService.test();
    }
}
