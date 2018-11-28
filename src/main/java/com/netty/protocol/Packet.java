package com.netty.protocol;

/**
 * 自定义数据包.
 */
public abstract class Packet {

    private Byte version = 1;

    public abstract  Byte getCommand();

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }
}
