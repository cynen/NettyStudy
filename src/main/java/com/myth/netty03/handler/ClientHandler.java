package com.myth.netty03.handler;

import com.myth.netty03.protocol.PacketCodeC;
import com.myth.netty03.protocol.request.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    // 模拟客户端已链接服务器就开始登陆.
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端准备发送  小飞侠  登录请求...");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserid(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("小飞侠");
        loginRequestPacket.setPassword("pwd");

        // 编码,准备发送
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(loginRequestPacket);

        ctx.channel().writeAndFlush(buffer);

        super.channelActive(ctx);
    }
}
