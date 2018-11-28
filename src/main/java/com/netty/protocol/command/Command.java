package com.netty.protocol.command;

/**
 * 指令集
 */
public interface Command {

    Byte LOGIN_REQUEST = 1; //登录请求.
    Byte LOGIN_RESPONSE = 2;// 登录响应

    Byte MESSAGE_REQUEST = 3; // Client端发送的消息
    Byte MESSAGE_RESPONSE = 4; // Server端回复的消息.

    Byte LOGOUT_REQUEST = 5; // 登出请求

    Byte LOGOUT_RESPONSE = 6; // 登出响应.

    Byte CREATE_GROUP_REQUEST = 7; // 建群请求

    Byte CREATE_GROUP_RESPONSE = 8; // 建群响应

}
