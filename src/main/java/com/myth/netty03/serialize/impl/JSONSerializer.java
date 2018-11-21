package com.myth.netty03.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.myth.netty03.serialize.MySerializer;
import com.myth.netty03.serialize.MySerializerAlgorithm;

public class JSONSerializer implements MySerializer {
    @Override
    public byte getSerializerAlgorithm() {
        return MySerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        // 将当前的对象,序列化成bytes数组,用于发送
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        // 将当前序列化后的数组,进行反序列化成java对象.
        return JSON.parseObject(bytes,clazz);
    }
}
