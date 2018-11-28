package com.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final  int lengthFieldOffset = 7;
    private static final  int lengthFieldLength = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, lengthFieldOffset, lengthFieldLength);
    }
    // 非自定义协议的数据,屏蔽掉.
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // System.out.println("解码....");
        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER){
            ctx.channel().close();
            return  null;
        }
        return super.decode(ctx, in);
    }
}
