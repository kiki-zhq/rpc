package com.rpc.application.mvc;

import com.rpc.application.ApplicationContext;
import com.rpc.application.netty.model.RpcRequest;
import com.rpc.application.netty.model.RpcResponse;
import com.rpc.enums.RpcCodeEnums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * rpc处理适配器
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public class RpcHandlerAdapter {

    /**
     * 进行处理请求
     *
     * @param request 请求
     * @return 请求结果
     * @author kiki
     * @since 2021/6/17 2:03 下午
     */
    public static RpcResponse handle(RpcRequest request) {
        RpcResponse response = new RpcResponse();
        try {
            response = invokeMethod(request.getClassName(), request.getMethodName(), request.getParameterType(), request.getParameterValue());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(RpcCodeEnums.ERROR.getValue());
        }
        return response;
    }

    /**
     * 执行所需要的方法
     *
     * @param className      类名字
     * @param methodName     方法名称
     * @param parameterType  参数类型
     * @param parameterValue 参数值
     * @return 结果
     * @author kiki
     * @since 2021/6/17 2:06 下午
     */
    private static RpcResponse invokeMethod(Class<?> className, String methodName, Class<?>[] parameterType, Object[] parameterValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //获取目标类的bean
        Object bean = ApplicationContext.getBean(className, true);
        //获取目标方法
        Method targetMethod = bean.getClass().getMethod(methodName, parameterType);
        //执行方法
        Object result = targetMethod.invoke(bean, parameterValue);
        return new RpcResponse(targetMethod.getReturnType(), result);
    }
}
