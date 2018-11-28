package com.netty.protocol.response;

import com.netty.protocol.Packet;
import com.netty.protocol.command.Command;

public class MessageResponsePacket  extends Packet {
    private String fromUserId;

    private String fromUserName;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
