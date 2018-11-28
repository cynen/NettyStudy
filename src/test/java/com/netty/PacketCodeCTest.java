package com.netty;

import com.netty.codec.PacketCodeC;
import com.netty.protocol.Packet;
import com.netty.protocol.request.LoginRequestPacket;
import com.netty.serialize.MySerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

public class PacketCodeCTest {
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUsername("nihao");

        // 对buf进行设置数据
        PacketCodeC.INSTANCE.encode(byteBuf,packet);
        // 解码.
        Packet packet1 = PacketCodeC.INSTANCE.decode(byteBuf);
        MySerializer serializer  = MySerializer.DEFAULT;
        // 自动化测试的目的:测试编码前后的对象的序列化的数据是否一样.
        // 为什么不直接使用Packet对象断言? 因为本来就是2个不相同的对象,只是数据一样而已.
        Assert.assertArrayEquals(serializer.serialize(packet),serializer.serialize(packet1));
    }

}
