package com.rpc.application.netty;

import com.rpc.application.netty.channel.RpcServerChannelInitializerImpl;
import com.rpc.application.netty.channel.HttpChannelInitializerImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <p>
 * 服务端
 * </p>
 *
 * @author kiki
 * @date 2021/6/8
 */
public class Server implements Runnable {

    /**
     * 端口号
     */
    private int port;


    public Server(int port) {
        this.port = port;
    }

    /**
     * 开启服务
     *
     * @author kiki
     * @since 2021/6/8 11:02 上午
     */
    @Override
    public void run() {
        //负责接收请求的分组
        EventLoopGroup acceptorGroup = new NioEventLoopGroup(1);
        //负责处理请求的分组
        EventLoopGroup handleGroup = new NioEventLoopGroup();
        try {
            //创建一个server
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(acceptorGroup, handleGroup)
                    //通道类型设置
                    //NioServerSocketChannel(用于服务端非阻塞地接收TCP连接）
                    //NioSocketChannel(用于维持非阻塞的TCP连接)
                    //NioDatagramChannel(用于非阻塞地处理UDP连接)
                    //OioServerSocketChannel(用于服务端阻塞地接收TCP连接)
                    //OioSocketChannel(用于阻塞地接收TCP连接)
                    //OioDatagramChannel(用于阻塞地处理UDP连接)
                    .channel(NioServerSocketChannel.class)
                    //设置配置  tcp握手时最大的消息队列大小
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //初始化请求通道的处理流程
                    .childHandler(new HttpChannelInitializerImpl())
                    //初始化rpc请求通道的处理过程
                    .childHandler(new RpcServerChannelInitializerImpl());
            //sync方法是等待异步操作执行完毕
            System.out.println("初始化成功，开启服务中");
            ChannelFuture cf = bootstrap.bind(port).sync();
            //对通道关闭进行监听，closeFuture是异步操作，监听通道关闭
            //通过sync方法同步等待通道关闭处理完毕，这里会阻塞等待通道关闭完成
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭接收请求的线程组
            acceptorGroup.shutdownGracefully();
            //关闭处理请求的线程组
            handleGroup.shutdownGracefully();
        }
    }

}
