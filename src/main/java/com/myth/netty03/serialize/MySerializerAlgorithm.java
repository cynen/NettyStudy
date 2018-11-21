package com.myth.netty03.serialize;

/**
 *
 */
public interface MySerializerAlgorithm {
    /**
     * 序列化的标志,目前使用1 表示JSON格式的序列化.
     */
    byte JSON = 1;
}
