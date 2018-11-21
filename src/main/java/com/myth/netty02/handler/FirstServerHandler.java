package com.myth.netty02.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 服务端是读,所以继承InBound类.
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 服务器直接将接受到的消息转换成ByteBuf.
        ByteBuf byteBuf = (ByteBuf) msg;

        // 输出接受到的消息.
        System.out.println(new Date() + ":服务器接受到客户端的数据:" + byteBuf.toString(Charset.forName("utf-8")));

        // 回送消息.
        ctx.channel().writeAndFlush(getByteBuf(ctx));
        // 链式调用,继续调用其他Handler.
        super.channelRead(ctx, msg);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 1. 获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        // 2. 准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = "你好，我是服务端的小飞侠!".getBytes(Charset.forName("utf-8"));

        // 3. 填充数据到 ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }

}
