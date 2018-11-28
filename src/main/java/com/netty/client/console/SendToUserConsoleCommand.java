package com.netty.client.console;

import com.netty.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    // MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("准备发送一对一消息,请输入 userid msg 格式");
        // 获得touserid,和msg
        String toUserId = scanner.next();
        // System.out.println("发送消息给");
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
