package com.netty.utils;

import com.netty.attribute.Attributes;
import io.netty.channel.Channel;

public class LoginUtils {


    public static boolean hasLogin(Channel channel){
        return channel.attr(Attributes.LOGINON).get()!=null;
    }


}
