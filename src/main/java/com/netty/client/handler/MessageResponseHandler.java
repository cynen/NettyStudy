package com.netty.client.handler;

import com.netty.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        // 接受消息方,收到了发送过来的消息.
        if (messageResponsePacket.isSucess()){
            String fromUserName = messageResponsePacket.getFromUserName();
            String fromUserId = messageResponsePacket.getFromUserId();

            System.out.println(fromUserName +"["+fromUserId+"] 说 : " + messageResponsePacket.getMessage());
        }else {
            System.err.println(messageResponsePacket.getMessage());
        }



    }
}
