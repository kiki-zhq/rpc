package com.rpc.application.proxy;

import com.rpc.application.netty.Client;
import com.rpc.application.netty.handle.RpcClientHandle;
import com.rpc.application.netty.model.RpcRequest;
import com.rpc.application.netty.model.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>
 * jdk动态代理处理
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public class ProxyInvocationHandler implements InvocationHandler {

    /**
     * 进行代理操作
     *
     * @param proxy  代理实例
     * @param method 方法
     * @param args   参数
     * @return 结果
     * @author kiki
     * @since 2021/6/17 6:29 下午
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //生成模型
        RpcRequest request = generateRpcRequest(proxy, method, args);
        //进行发送请求
        Client.channel.writeAndFlush(request);
        RpcResponse response;
        //获取响应结果
        int count = 0;
        while (true) {
            if (count == 3) {
                throw new RuntimeException("请求超时, 请检查服务是否正常");
            }
            response = RpcClientHandle.responseMap.get(request.getUnionId());
            if (response != null) {
                break;
            }
            count++;
            Thread.sleep(1000);
        }
        return response.getReturnValue();
    }


    /**
     * 生成模型
     *
     * @param proxy  代理实例
     * @param method 方法
     * @param args   参数
     * @return 模型
     * @author kiki
     * @since 2021/6/17 6:38 下午
     */
    private RpcRequest generateRpcRequest(Object proxy, Method method, Object[] args) {
        RpcRequest request = new RpcRequest();
        request.setUnionId(Thread.currentThread().getName() + Thread.currentThread().getId());
        request.setClassName(proxy.getClass());
        request.setMethodName(method.getName());
        request.setParameterType(method.getParameterTypes());
        request.setParameterValue(args);
        return request;
    }
}
