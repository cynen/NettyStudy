package com.netty.client.handler;

import com.netty.protocol.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CreateGroupResponsehandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupResponsePacket createGroupResponsePacket) throws Exception {
        // 读取到服务器返回的消息.
        if (createGroupResponsePacket.isSuccess()){
            System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
            System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());
        }else {
            System.out.println("建群失败.");
        }


    }
}
