package com.myth.netty03.protocol.response;

import com.myth.netty03.protocol.Packet;
import com.myth.netty03.protocol.command.Command;

public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

}
