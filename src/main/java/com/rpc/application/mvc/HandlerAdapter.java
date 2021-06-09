package com.rpc.application.mvc;

import com.alibaba.fastjson.JSONObject;
import com.rpc.annotation.RequestParam;
import com.rpc.application.ApplicationContext;
import com.rpc.enums.RequestMethodEnum;
import com.rpc.model.UrlMappingModel;
import com.rpc.utils.CheckUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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
     * @param content       请求体
     * @param requestUrl    请求地址
     * @param requestMethod 请求方法
     * @return 返回结果
     * @author zhq
     * @since 2021/6/7 3:44 下午
     */
    public static Object handle(String content, String requestUrl, String requestMethod) {
        try {
            RequestMethodEnum method = RequestMethodEnum.getMethodEnum(requestMethod);
            URL url = new URL(requestUrl);

            UrlMappingModel model = handleMapping(url.getPath(), method);
            if (CheckUtils.isEmpty(model) || model.getTargetClass() == null || model.getMethodName() == null) {
                return null;
            }
            return invokeMethod(url.getQuery(), content, model, RequestMethodEnum.isQuery(method));

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
            urlMappingModel.setMethodArgsType(targetMethod.getParameterTypes());
            urlMappingModel.setMethodReturnType(targetMethod.getReturnType());
            break;
        }


        urlMappingModel.setTargetClass(Handler.getClassMappingMap().get(classPath.get()));
        return urlMappingModel;

    }

    /**
     * 执行所需要的方法
     *
     * @param query   参数
     * @param content 请求体
     * @param model   模型
     * @param isQuery 是否为参数
     * @return 返回结果
     * @author zhq
     * @since 2021/6/7 5:04 下午
     */
    private static Object invokeMethod(String query, String content, UrlMappingModel model, boolean isQuery) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //获取目标类的bean
        Object targetInstance = ApplicationContext.getBean(model.getTargetClass());
        //获取目标方法
        Method targetMethod = model.getTargetClass().getMethod(model.getMethodName(), model.getMethodArgsType());
        //获取方法的参数
        Object[] args = getMethodParameters(targetMethod.getParameters(), query, content, isQuery);
        //执行目标方法
        return targetMethod.invoke(targetInstance, args);
    }

    /**
     * 对应参数
     *
     * @param params  方法参数类型
     * @param query   参数
     * @param content 请求体
     * @param isQuery 是否参数
     * @return 参数
     * @author kiki
     * @since 2021/6/9 10:46 上午
     */
    private static Object[] getMethodParameters(Parameter[] params, String query, String content, boolean isQuery) {
        Map<String, Object> body = new HashMap<>();
        if (isQuery) {
            if (query != null) {
                List<String> list = Arrays.stream(query.split("&")).collect(Collectors.toList());
                for (String keyValue : list) {
                    String[] string = keyValue.split("=");
                    body.put(string[0], string[1]);
                }
            }
        } else {
            if (content != null) {
                body = JSONObject.parseObject(content, Map.class);
            }
        }
        //设置方法的参数
        return getMethodParameters(body, params);

    }

    /**
     * 设置方法的参数
     *
     * @param body   请求参数
     * @param params 参数列表
     * @return 方法参数
     * @author kiki
     * @since 2021/6/9 11:39 上午
     */
    private static Object[] getMethodParameters(Map<String, Object> body, Parameter[] params) {
        Object[] methodParameters = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            if (params[i].isAnnotationPresent(RequestParam.class)) {
                methodParameters[i] = body.get(params[i].getAnnotation(RequestParam.class).value());
            } else {
                methodParameters[i] = body.get(params[i].getName());
            }
        }
        return methodParameters;
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
