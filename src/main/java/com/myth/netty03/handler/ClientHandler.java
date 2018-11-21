package com.myth.netty03.handler;

import com.myth.netty03.protocol.Packet;
import com.myth.netty03.protocol.PacketCodeC;
import com.myth.netty03.protocol.request.LoginRequestPacket;
import com.myth.netty03.protocol.response.LoginResponsePacket;
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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 服务端读取客户端闪送的数据.
        ByteBuf requestByte = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(requestByte);
        if (packet instanceof LoginResponsePacket){
            // 登录结果展示.
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()){
                System.out.println("登录成功");
            }else {
                System.out.println("登录失败");
            }

        }
    }
}
