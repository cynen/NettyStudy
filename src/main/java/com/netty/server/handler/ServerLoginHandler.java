package com.netty.server.handler;

import com.netty.protocol.request.LoginRequestPacket;
import com.netty.protocol.response.LoginResponsePacket;
import com.netty.session.Session;
import com.netty.utils.IDUtils;
import com.netty.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
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
            // 如果用户校验通过,name表示用户登录成功.
            responsePacket.setSuccess(true);
            String userid = IDUtils.getUUID();
            responsePacket.setUserId(userid);
            // 创建一个Session,绑定到当前的channel
            Session session = new Session(userid,packet.getUsername());
            // 将当前登录的客户端与对应的Session绑定. (此时是服务器端的操作.)
            SessionUtils.bindSession(ctx.channel(),session);

        }else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("用户名或密码校验不通过");
        }
        ctx.channel().writeAndFlush(responsePacket);
    }
    private boolean validateUser(LoginRequestPacket packet){
        return true;
    }

}
