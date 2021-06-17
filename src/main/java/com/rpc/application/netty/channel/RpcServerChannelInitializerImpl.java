package com.rpc.application.netty.channel;

import com.rpc.application.netty.handle.RpcServerHandle;
import com.rpc.application.netty.model.RpcRequest;
import com.rpc.application.netty.model.RpcResponse;
import com.rpc.application.netty.rpc.RpcDecoder;
import com.rpc.application.netty.rpc.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * <p>
 * rpc服务端通道初始化
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public class RpcServerChannelInitializerImpl extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化通道
     *
     * @param ch 通道
     * @author kiki
     * @since 2021/6/17 4:46 下午
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                //解决粘包黏包
                .addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                //自定义解码器
                .addLast(new RpcDecoder(RpcRequest.class))
                //自定义编码器
                .addLast(new RpcEncoder(RpcResponse.class))
                //处理过程
                .addLast(new RpcServerHandle());
    }
}
