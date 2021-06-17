package com.rpc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 *
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public class SerializationUtils {


    /**
     * 编码
     *
     * @param msg 对象
     * @return 字节码
     * @author kiki
     * @since 2021/6/17 11:45 上午
     */
    public static <T> byte[] serialize(T msg) {
        return JSON.toJSONString(msg).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 解码
     *
     * @param bytes 字节码
     * @param type  类型
     * @return 返回对象
     * @author kiki
     * @since 2021/6/17 11:43 上午
     */
    public static <T> T deserialize(byte[] bytes, Class<T> type) {
        return JSONObject.parseObject(bytes, type);
    }

    /**
     * 转化实体类
     *
     * @param msg  信息
     * @param type 类型
     * @return 实体类
     * @author kiki
     * @since 2021/6/17 9:53 下午
     */
    public static <T> T parse(Object msg, Class<T> type) {
        return JSON.toJavaObject(JSONObject.parseObject(JSON.toJSONString(msg)), type);
    }
}
