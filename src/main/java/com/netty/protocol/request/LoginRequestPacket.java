package com.netty.protocol.request;

import com.netty.protocol.Packet;
import com.netty.protocol.command.Command;

public class LoginRequestPacket extends Packet {
    private String userid;
    private  String username;
    private String pwd;
    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
