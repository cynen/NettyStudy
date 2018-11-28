package com.netty.protocol.request;

import com.netty.protocol.Packet;
import com.netty.protocol.command.Command;

public class MessageRequestPacket extends Packet {
    private String msg;
    private String toUserId;

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public MessageRequestPacket(String toUserId, String msg){
        this.msg = msg;
        this.toUserId = toUserId;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
