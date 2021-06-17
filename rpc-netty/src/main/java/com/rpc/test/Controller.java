package com.rpc.test;

import com.rpc.annotation.Autowire;
import com.rpc.annotation.RequestMapping;
import com.rpc.annotation.RequestParam;
import com.rpc.annotation.RestController;
import com.rpc.test.service.ServiceImpl;
import com.rpc.test.service.TestServiceImpl;

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

    @Autowire
    private TestServiceImpl testService;

    @RequestMapping("/get")
    public void say(@RequestParam("title") String title, String content) {
        System.out.println("title:" + title + "content:" + content);
        service.say();
        testService.test();
    }
}
