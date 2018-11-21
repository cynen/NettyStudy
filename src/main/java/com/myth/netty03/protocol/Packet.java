package com.myth.netty03.protocol;

public abstract class Packet {
    /**
     * 定义协议版本号.
     */
    private Byte version = 1;

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }

    /**
     * 指令.
     * @return
     */
    public abstract Byte getCommand();
}
