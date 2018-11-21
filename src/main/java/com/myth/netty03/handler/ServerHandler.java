package com.myth.netty03.handler;

import com.myth.netty03.protocol.Packet;
import com.myth.netty03.protocol.PacketCodeC;
import com.myth.netty03.protocol.request.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 服务端读取客户端闪送的数据.

        ByteBuf requestByte = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requestByte);
        if (packet instanceof LoginRequestPacket){
            // 登录流程.
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            System.out.println(loginRequestPacket.getUsername() + "登录成功....");

        }


        super.channelRead(ctx, msg);
    }
}
