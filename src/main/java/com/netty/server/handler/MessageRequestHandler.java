package com.netty.server.handler;

import com.netty.protocol.request.MessageRequestPacket;
import com.netty.protocol.response.MessageResponsePacket;
import com.netty.session.Session;
import com.netty.utils.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * SimpleChannelInboundHandler
 * 会自动匹配packet包的类型,所有的被SimpleChannelInboundHandler 管理的类型只会被其中一个handler处理.
 * 所以,处理了请求的handler.就不可能处理message.
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        // 1.获得消息发送方的Session信息
        Session seesion = SessionUtils.getSeesion(ctx.channel());
        // 2.获得发送的具体的消息内容.构建一个发送给指定对象的消息体.
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(seesion.getUserId());
        messageResponsePacket.setFromUserName(seesion.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMsg());

        // 3.获得消息接收方的Channel
        Channel toUserChannel = SessionUtils.getChannel(messageRequestPacket.getToUserId());

        if (toUserChannel != null && SessionUtils.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(messageResponsePacket);
        }else {
            // 这个消息,原本是应该回送给发送方.
            messageResponsePacket.setSucess(false);
            messageResponsePacket.setMessage("["+messageRequestPacket.getToUserId()+"]不在线,发送消息失败");
            ctx.channel().writeAndFlush(messageResponsePacket);
        }



    }
}
