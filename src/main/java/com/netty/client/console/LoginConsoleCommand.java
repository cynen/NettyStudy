package com.netty.client.console;

import com.netty.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 登录请求.
 */
public class LoginConsoleCommand implements ConsoleCommand{
    LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
    @Override
    public void exec(Scanner scanner, Channel channel) {
        // 尚未登录
        System.out.print("输入用户名登录: ");
        String username = scanner.nextLine();
        loginRequestPacket.setUsername(username);

        // 密码使用默认的
        loginRequestPacket.setPwd("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
