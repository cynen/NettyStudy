package com.netty.client.handler;

import com.netty.attribute.Attributes;
import com.netty.protocol.response.LoginResponsePacket;
import com.netty.session.Session;
import com.netty.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientLoginHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket packet) throws Exception {
        String userName = packet.getUserName();
        String userId = packet.getUserId();

        if (packet.isSuccess()){
            System.out.println("["+userName+"]登录成功,用户Id为:" + userId);
            // 登录成功,构建本地Session
            SessionUtils.bindSession(ctx.channel(),new Session(userId,userName));
            // ctx.channel().attr(Attributes.LOGINON).set(true);
        }else {
            System.out.println("["+userName+"]登录失败,原因:" + packet.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端关闭");
        super.channelInactive(ctx);
    }
}
