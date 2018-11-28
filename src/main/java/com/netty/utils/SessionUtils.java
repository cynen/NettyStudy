package com.netty.utils;

import com.netty.attribute.Attributes;
import com.netty.session.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtils {

    private static final Map<String,Channel> userIdChannelMap = new ConcurrentHashMap<>();

    // 判断登录,直接判断时候有session
    public static boolean hasLogin(Channel channel){
        return channel.attr(Attributes.SESSION).get()!=null;
    }

    // 绑定session
    public static void bindSession(Channel channel, Session session){
        //同时,在map中进行管理.
        userIdChannelMap.put(session.getUserId(),channel);
        channel.attr(Attributes.SESSION).set(session);
    }
    // 解除绑定
    public static void unBindSession(Channel channel, Session session){
        userIdChannelMap.remove(session.getUserId());
        channel.attr(Attributes.SESSION).set(null);
    }

    public static  Session getSeesion(Channel channel){
        return  channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId){
        return userIdChannelMap.get(userId);
    }
}
