package com.rpc.application.netty.handle;

import com.rpc.application.netty.model.RpcRequest;
import com.rpc.application.netty.model.RpcResponse;
import com.rpc.application.mvc.RpcHandlerAdapter;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <p>
 * Rpc服务处理
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public class RpcServerHandle extends SimpleChannelInboundHandler<RpcRequest> {

    /**
     * 负责线程处理逻辑
     *
     * @param ctx 处理信息
     * @param msg 请求信息
     * @author kiki
     * @since 2021/6/9 10:30 上午
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        //执行
        RpcResponse response = RpcHandlerAdapter.handle(msg);
        //返回  异步去关闭链接
        ctx.writeAndFlush(response).addListener((ChannelFutureListener) future -> ctx.close());
    }

}
