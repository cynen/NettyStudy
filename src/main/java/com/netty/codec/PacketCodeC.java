package com.netty.codec;

import com.netty.protocol.Packet;
import com.netty.protocol.command.Command;
import com.netty.protocol.request.CreateGroupRequestPacket;
import com.netty.protocol.request.LoginRequestPacket;
import com.netty.protocol.request.MessageRequestPacket;
import com.netty.protocol.response.CreateGroupResponsePacket;
import com.netty.protocol.response.LoginResponsePacket;
import com.netty.protocol.response.MessageResponsePacket;
import com.netty.serialize.MySerializer;
import com.netty.serialize.impl.JSONSerialize;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;


/**
 * 编码解码的类.
 * 注意区分序列化.
 */
public class PacketCodeC {
    // 魔数.
    public static final int MAGIC_NUMBER = 0x12345678;
    // 单例
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, MySerializer> serializerMap;


    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

        packetTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);

        serializerMap = new HashMap<>();
        MySerializer serializer =  new JSONSerialize(); // 自定义序列化
        serializerMap.put(serializer.getSerializeAlgorithm(), serializer);
    }
    /**
     * 数据编码.
     * 将数据编码到ByteBuf中.
     */
    public void encode(ByteBuf buf , Packet packet){
        // 将具体的数据包,序列化为数组.
        byte[] bytes = MySerializer.DEFAULT.serialize(packet);

        // 编码
        buf.writeInt(MAGIC_NUMBER);// 魔数
        buf.writeByte(packet.getVersion()); // 版本号
        buf.writeByte(MySerializer.DEFAULT.getSerializeAlgorithm());//算法
        buf.writeByte(packet.getCommand()); // 操作指令
        buf.writeInt(bytes.length); // 数据长度
        buf.writeBytes(bytes); // 写数据.
    }

    /**
     * 数据解码.
     * 返回数据包
     */
    public Packet decode(ByteBuf byteBuf){
        // 获得数据后,分段进行解析

        // 1.魔数
        byteBuf.skipBytes(4);
        //2. 版本号
        byte version = byteBuf.readByte();
        // 3.算法
        byte serializeAlgorithm = byteBuf.readByte();
        // 4.操作指令
        byte command = byteBuf.readByte();
        // 5.数据长度
        int length = byteBuf.readInt();
        // 6.具体数据.
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        // 获得具体的算法与packet对象.
        Class<? extends  Packet> clazz = getPacketClass(command);
        MySerializer serializer = getSerialize(serializeAlgorithm);

        if (clazz != null && serializer != null){
            // 有对应的数据.
            return  serializer.deserialize(clazz,bytes);
        }
        return  null;
    }

    // 获得序列化对象.
    private MySerializer getSerialize(byte serializeAlgorithm) {
        return  serializerMap.get(serializeAlgorithm);
    }
    // 获得包对象.
    private Class<? extends Packet> getPacketClass(byte command) {
        return  packetTypeMap.get(command);
    }


}
