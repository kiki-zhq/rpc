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
 * @date 2021/6/6
 */
@RestController
@RequestMapping("/test")
public class Controller {

    @Autowire
    private ServiceImpl service;

    @RequestMapping("/get")
    public void say() {
        System.out.println("hello");
    }
}
