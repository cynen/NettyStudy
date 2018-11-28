package com.netty.protocol.response;

import com.netty.protocol.Packet;

public class MessageResponsePacket  extends Packet {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Byte getCommand() {
        return null;
    }
}
