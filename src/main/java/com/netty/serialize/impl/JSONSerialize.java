package com.netty.serialize.impl;


import com.alibaba.fastjson.JSON;
import com.netty.protocol.Packet;
import com.netty.serialize.MySerializer;
import com.netty.serialize.SerializeAlgorithm;

public class JSONSerialize implements MySerializer {
    @Override
    public byte getSerializeAlgorithm() {
        return SerializeAlgorithm.JSONSER;
    }

    @Override
    public byte[] serialize(Packet packet) {
        return JSON.toJSONBytes(packet);
    }

    @Override
    public Packet deserialize(Class<? extends Packet> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
