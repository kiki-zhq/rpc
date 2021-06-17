package com.rpc.application.netty;

import com.rpc.application.netty.channel.RpcClientChannelInitializerImpl;
import com.rpc.application.netty.model.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <p>
 * 客户端
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public class RpcClient implements Runnable {
    /**
     * 端口
     */
    private final int port;

    /**
     * 地址
     */
    private final String host;

    /**
     * 请求
     */
    private final RpcRequest request;

    /**
     * 通道
     */
    public static Channel channel = null;

    public RpcClient(String host, int port, RpcRequest request) {
        this.host = host;
        this.port = port;
        this.request = request;
    }

    /**
     * 启动客户端
     *
     * @author kiki
     * @since 2021/6/17 5:31 下午
     */
    @Override
    public void run() {
        try {
            //工作线程
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new RpcClientChannelInitializerImpl());
            channel = bootstrap.connect(host, port).sync().channel();
            channel.writeAndFlush(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
