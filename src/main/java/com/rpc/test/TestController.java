package com.rpc.test;

import com.rpc.annotation.Autowire;
import com.rpc.annotation.RequestMapping;
import com.rpc.annotation.RestController;
import com.rpc.test.service.ServiceImpl;

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
    private ServiceImpl service;

    @RequestMapping("/test")
    public void test() {
        service.say();
    }
}
