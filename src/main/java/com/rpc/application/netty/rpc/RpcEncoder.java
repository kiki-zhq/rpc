package com.rpc.application.netty.rpc;

import com.rpc.utils.SerializationUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <p>
 * rpc自定义编码器
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public class RpcEncoder extends MessageToByteEncoder<Object> {

    private final Class<?> targetClass;

    public RpcEncoder(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    /**
     * 进行编码操作
     *
     * @param ctx 处理信息
     * @param msg 信息
     * @param out 字节缓存区
     * @author kiki
     * @since 2021/6/17 11:31 上午
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (targetClass.isInstance(msg)) {
            byte[] news = SerializationUtils.serialize(msg);
            out.writeInt(news.length);
            out.writeBytes(news);
        }
    }
}
