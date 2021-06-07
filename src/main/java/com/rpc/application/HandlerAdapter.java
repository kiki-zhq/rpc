package com.rpc.application;

import com.rpc.enums.RequestMethodEnum;
import com.rpc.model.UrlMappingModel;
import com.rpc.utils.CheckUtils;
import io.netty.handler.codec.http.HttpRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * <p>
 * 路径适配器
 * </p>
 *
 * @author kiki
 * @date 2021/6/7
 */
public class HandlerAdapter {

    /**
     * 处理执行链
     *
     * @param request       请求
     * @param requestUrl    请求地址
     * @param requestMethod 请求方法
     * @return 返回结果
     * @author zhq
     * @since 2021/6/7 3:44 下午
     */
    public static Object handle(HttpRequest request, String requestUrl, String requestMethod) {
        try {
            RequestMethodEnum method = RequestMethodEnum.getMethodEnum(requestMethod);
            URL url = new URL(requestUrl);

            UrlMappingModel model = handleMapping(url.getPath(), method);
            if (CheckUtils.isEmpty(model) || model.getTargetClass() == null || model.getMethodName() == null) {
                return null;
            }
            return invokeMethod(request, model);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理过程
     *
     * @param path   地址路径
     * @param method 请求方法
     * @return 执行对象
     * @author zhq
     * @since 2021/6/7 3:54 下午
     */
    private static UrlMappingModel handleMapping(String path, RequestMethodEnum method) {
        UrlMappingModel urlMappingModel = new UrlMappingModel();

        Optional<String> classPath = Handler.getClassMappingMap().keySet().stream().filter(path::contains).findFirst();
        if (!classPath.isPresent()) {
            return null;
        }
        path = path.replace(classPath.get(), "");
        Set<String> methodMappingSet = Handler.getMethodMappingMap().getOrDefault(path, new HashSet<>());
        Class<?> targetClass = Handler.getClassMappingMap().get(classPath.get());
        for (Method targetMethod : targetClass.getMethods()) {
            if (!methodMappingSet.contains(targetMethod.getName())) {
                continue;
            }
            //验证是否该请求方式
            if (!isValidMethod(method, path)) {
                return null;
            }
            urlMappingModel.setMethodName(targetMethod.getName());
            break;
        }


        urlMappingModel.setTargetClass(Handler.getClassMappingMap().get(classPath.get()));
        return urlMappingModel;

    }

    /**
     * 执行所需要的方法
     *
     * @param request 请求
     * @param model   模型
     * @return 返回结果
     * @author zhq
     * @since 2021/6/7 5:04 下午
     */
    private static Object invokeMethod(HttpRequest request, UrlMappingModel model) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //获取目标类的bean
        Object targetInstance = ApplicationContext.getBean(model.getTargetClass());
        //获取目标方法
        Method targetMethod = model.getTargetClass().getMethod(model.getMethodName());
        //执行目标方法
        return targetMethod.invoke(targetInstance);
    }

    /**
     * 验证是否该请求方式
     *
     * @param method 请求方法
     * @param path   路径
     * @return 验证
     * @author zhq
     * @since 2021/6/7 4:47 下午
     */
    private static boolean isValidMethod(RequestMethodEnum method, String path) {
        Set<String> methodNameSet = Handler.getRequestMethodEnumMap().getOrDefault(method, new HashSet<>());
        methodNameSet.addAll(Handler.getRequestMethodEnumMap().getOrDefault(RequestMethodEnum.ALL, new HashSet<>()));
        return methodNameSet.contains(path);
    }

}
