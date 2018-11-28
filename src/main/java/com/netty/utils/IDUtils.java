package com.netty.utils;

import java.util.UUID;

/**
 * 工具类
 */
public class IDUtils {

    public  static   String getUUID(){
        return UUID.randomUUID().toString().substring(0,8);
    }
}
