package com.rpc.application.netty.channel;

import com.rpc.application.netty.handle.HttpServerHandle;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * <p>
 * 通道初始化实现方法
 * </p>
 *
 * @author kiki
 * @date 2021/6/8
 */
public class HttpChannelInitializerImpl extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化通道
     *
     * @param ch 通道
     * @author kiki
     * @since 2021/6/8 11:29 上午
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //添加编码器
        ch.pipeline().addLast(new HttpRequestDecoder());
        ch.pipeline().addLast(new HttpObjectAggregator(65535));
        ch.pipeline().addLast(new HttpResponseEncoder());
        ch.pipeline().addLast(new ChunkedWriteHandler());
        ch.pipeline().addLast(new HttpServerHandle());
    }
}
