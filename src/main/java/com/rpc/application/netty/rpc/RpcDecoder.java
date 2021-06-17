package com.rpc.application.netty.rpc;

import com.rpc.utils.SerializationUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * <p>
 * rpc自定义解码器
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private final Class<?> targetClass;

    private final static int BYTE_LENGTH = 4;

    public RpcDecoder(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    /**
     * 解码操作
     *
     * @param ctx 处理信息
     * @param in  字符缓存区
     * @param out 接收参数
     * @author kiki
     * @since 2021/6/17 11:34 上午
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //空消息则直接返回
        if (in.readableBytes() < BYTE_LENGTH) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        //网络传输可能会丢包
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        byte[] data = new byte[dataLength];
        in.readBytes(data);
        Object target = SerializationUtils.deserialize(data, targetClass);
        out.add(target);
    }
}
