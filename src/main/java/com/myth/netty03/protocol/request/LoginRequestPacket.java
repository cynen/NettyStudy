package com.myth.netty03.protocol.request;

import com.myth.netty03.protocol.Packet;
import com.myth.netty03.protocol.command.Command;


public class LoginRequestPacket extends Packet {
    private String userid;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
