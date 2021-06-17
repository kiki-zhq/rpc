package com.rpc.application.netty.handle;

import com.rpc.application.netty.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * rpc客户端处理
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public class RpcClientHandle extends SimpleChannelInboundHandler<RpcResponse> {

    /**
     * 响应结果 key->线程id  value->返回结果
     */
    public static Map<String, RpcResponse> responseMap = new ConcurrentHashMap<>();

    /**
     * 接收服务端返回来的数据
     *
     * @param ctx 处理信息
     * @param msg 返回结果
     * @author kiki
     * @since 2021/6/17 5:57 下午
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        responseMap.put(msg.getUnionId(), msg);
    }
}
