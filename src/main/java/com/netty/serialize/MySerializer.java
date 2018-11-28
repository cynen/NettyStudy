package com.netty.serialize;

import com.netty.protocol.Packet;
import com.netty.serialize.impl.JSONSerialize;

/**
 * 自定义序列化
 * 提供3个必须的方法.
 *
 * 1. 获得序列化的算法.
 * 2. 序列化
 * 3.反序列化.
 *
 */
public interface MySerializer {

    MySerializer DEFAULT = new JSONSerialize();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializeAlgorithm();

    /**
     * 序列化
     * @param packet
     * @return
     */
    byte[] serialize(Packet packet);

    /**
     * 反序列化
     * @param clazz
     * @return
     */
    Packet deserialize(Class<? extends  Packet> clazz,byte[] bytes);

}
