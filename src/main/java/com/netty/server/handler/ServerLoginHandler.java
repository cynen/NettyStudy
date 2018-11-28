package com.netty.server.handler;

import com.netty.codec.PacketCodeC;
import com.netty.protocol.request.LoginRequestPacket;
import com.netty.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class ServerLoginHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket packet) throws Exception {
        System.out.println("接受到了请求数据.");
        // 设置返回值.
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(packet.getVersion());
        responsePacket.setUserId(packet.getUserid());
        responsePacket.setUserName(packet.getUsername());

        if (validateUser(packet)){
            responsePacket.setSuccess(true);
            String userid = getUserid();
            responsePacket.setUserId(userid);

        }else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("用户名或密码校验不通过");
        }
        ctx.channel().writeAndFlush(responsePacket);
    }
    private boolean validateUser(LoginRequestPacket packet){
        return true;
    }
    private  String getUserid(){
        return UUID.randomUUID().toString().substring(0,8);
    }
}
