package com.rpc.application.netty;

import com.alibaba.fastjson.JSON;
import com.rpc.application.mvc.HandlerAdapter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;

/**
 * <p>
 *
 * </p>
 *
 * @author kiki
 * @date 2021/6/8
 */
public class ServerHandle extends SimpleChannelInboundHandler<FullHttpRequest> {


    /**
     * 当客户端连接服务器完成就会触发该方法
     *
     * @param ctx 处理信息
     * @author kiki
     * @since 2021/6/8 11:21 上午
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    /**
     * 负责线程处理逻辑
     *
     * @param ctx 处理信息
     * @param msg 请求信息
     * @author kiki
     * @since 2021/6/9 10:30 上午
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        Object result = HandlerAdapter.handle(msg.content().toString(CharsetUtil.UTF_8), getUrl(ctx, msg), msg.method().name());
        responseResult(ctx, result);
    }

    /**
     * 生成地址信息
     *
     * @param ctx 处理信息
     * @param msg 请求信息
     * @return 地址
     * @author kiki
     * @since 2021/6/9 10:59 上午
     */
    private String getUrl(ChannelHandlerContext ctx, FullHttpRequest msg) {
        SocketAddress address = ctx.pipeline().channel().localAddress();
        return msg.protocolVersion().protocolName() + ":/" + address.toString() + msg.uri();
    }

    /**
     * 当通道读取完成后触发事件，即将返回后处理
     *
     * @param ctx 处理信息
     * @author kiki
     * @since 2021/6/8 11:52 上午
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 异常处理
     *
     * @param ctx   处理消息
     * @param cause 异常原因
     * @author kiki
     * @since 2021/6/8 11:24 上午
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }


    /**
     * 响应结果
     *
     * @param ctx     处理详细
     * @param result  结果
     * @author kiki
     * @since 2021/6/8 6:26 下午
     */
    private void responseResult(ChannelHandlerContext ctx, Object result) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(JSON.toJSONString(result), CharsetUtil.UTF_8));
        response.headers().set("Content-Type", "text/plain;charset=UTF-8;");
        response.headers().set("Content-Length", response.content().readableBytes());
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
