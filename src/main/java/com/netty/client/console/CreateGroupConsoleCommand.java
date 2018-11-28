package com.netty.client.console;

import com.netty.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("准备建群,请输入用户id列表.使用,隔开");
        // 初略的处理.用户登录完成之后,
        // 输入任意指令, 后面拼接上所有用户id,即可建群.
        String useridList = scanner.next();
        CreateGroupRequestPacket packet = new CreateGroupRequestPacket();
        for (String userId: useridList.split(",")){
            packet.getUserIdList().add(userId);
        }
        channel.writeAndFlush(packet);
    }
}
