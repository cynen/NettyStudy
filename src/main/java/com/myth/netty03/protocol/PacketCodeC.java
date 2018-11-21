package com.myth.netty03.protocol;

import com.alibaba.fastjson.JSON;
import com.myth.netty03.protocol.command.Command;
import com.myth.netty03.protocol.request.LoginRequestPacket;
import com.myth.netty03.protocol.response.LoginResponsePacket;
import com.myth.netty03.serialize.MySerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义编解码.
 */
public class PacketCodeC {
    // 魔法数字.为了限制应用层http协议自动解析做一个前缀.
    private static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();
    // 2个Map对象,分别存储Packet的映射
    private  static  Map<Byte,Class<? extends Packet>> packetTypeMap;
    // 存储序列化方法的Map
    private static   Map<Byte,MySerializer> serializerMap;

    // 静态代码块初始化其对象.
    static {
        // 一定要配置对应的Command指令.
        packetTypeMap = new ConcurrentHashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);

        serializerMap = new ConcurrentHashMap<>();
        MySerializer  serializer = MySerializer.DEFAULT;
        serializerMap.put(serializer.getSerializerAlgorithm(),serializer);

    }



    // 编码.将Packet对象,编码成ByteBuf对象.
    public ByteBuf encode(Packet packet){
        // 1.创建ByteBuf对象,用于编码后返回的
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 2.序列化packet对象.
        byte[] bytes = MySerializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程.
        // 创建一个我们自定义协议的数据.
        // 魔数4字节 + 版本号1字节+算法1字节+指令1字节+数据长度4字节+具体数据.
        byteBuf.writeInt(MAGIC_NUMBER); //魔数4字节
        byteBuf.writeByte(packet.getVersion());// 版本号1字节
        byteBuf.writeByte(MySerializer.DEFAULT.getSerializerAlgorithm()); //算法.
        byteBuf.writeByte(packet.getCommand());//指令1字节
        byteBuf.writeInt(bytes.length);// 数据的长度4字节
        byteBuf.writeBytes(bytes); // 具体的数据.

        return  byteBuf;
    }

    /**
     * 解码.根据自定义协议的内容,进行一个逐步解码.
     *
     * @param byteBuf
     * @return
     */
    public Packet decode(ByteBuf byteBuf){
        // 1.跳过魔数.
        byteBuf.skipBytes(4);
        // 2.跳过版本
        byteBuf.skipBytes(1);

        // 序列化算法:
        byte serializeAlgorithm  = byteBuf.readByte(); //读一个字节.

        //操作指令代码
        byte command = byteBuf.readByte(); // 1字节.

        // 数据长度
        int length = byteBuf.readInt(); // 读4字节.

        // 具体的数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command); // 得到的是具体的packet的类型class.
        MySerializer serializer = getSerializer(serializeAlgorithm);// 得到具体的序列化算法.

        if (requestType != null && serializer != null){
            // 只有当请求类型不为空,并且序列化算法不为空的时候,才会进行反序列化.
            return serializer.deserialize(requestType,bytes);
        }
        return null;
    }

    private MySerializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
