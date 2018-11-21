package com.myth.netty03.serialize;

import com.myth.netty03.serialize.impl.JSONSerializer;

/**
 * 序列化定义了 Java 对象与二进制数据的互转过程
 */
public interface MySerializer {
    MySerializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
