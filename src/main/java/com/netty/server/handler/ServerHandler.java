package com.netty.server.handler;

import com.netty.codec.PacketCodeC;
import com.netty.protocol.Packet;
import com.netty.protocol.request.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginRequestPacket){


        }else {

        }




        super.channelRead(ctx, msg);
    }
    private boolean validateUser(LoginRequestPacket packet){
        return true;
    }
}
