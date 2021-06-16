package com.rpc.test;

import com.rpc.annotation.Autowire;
import com.rpc.annotation.RequestMapping;
import com.rpc.annotation.RestController;
import com.rpc.test.service.ServiceImpl;
import com.rpc.test.service.TestServiceImpl;

/**
 * <p>
 *
 * </p>
 *
 * @author kiki
 * @date 2021/6/7
 */
@RestController
@RequestMapping("/controller")
public class TestController {

    @Autowire
    private TestServiceImpl service;

    @RequestMapping("/test")
    public void test() {
        System.out.println("hello");
        service.test();
    }
}
