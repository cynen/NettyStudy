package com.myth.netty03.protocol.request;

import com.myth.netty03.protocol.Packet;
import com.myth.netty03.protocol.command.Command;


public class LoginRequestPacket extends Packet {
    private Integer userid;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
