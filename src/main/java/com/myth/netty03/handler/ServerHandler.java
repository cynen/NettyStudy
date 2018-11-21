package com.myth.netty03.handler;

import com.myth.netty03.protocol.Packet;
import com.myth.netty03.protocol.PacketCodeC;
import com.myth.netty03.protocol.request.LoginRequestPacket;
import com.myth.netty03.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
         // 服务端读取客户端闪送的数据.
        ByteBuf requestByte = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(requestByte);
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        if (packet instanceof LoginRequestPacket){
            // 登录流程.
            // 校验数据
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            if(valid(loginRequestPacket)){
                System.out.println(loginRequestPacket.getUsername() + "登录成功....");
                responsePacket.setSuccess(true);
            }else {
                responsePacket.setSuccess(false);
                responsePacket.setReason("账号或者密码错误");
            }
            responsePacket.setVersion(packet.getVersion());
            // 登录响应
            ByteBuf res = PacketCodeC.INSTANCE.encode(responsePacket);
            ctx.channel().writeAndFlush(res);
        }


        super.channelRead(ctx, msg);
    }
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return  "小飞侠".equals(loginRequestPacket.getUsername());
    }
}
