package com.netty.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

public interface ConsoleCommand {
    public void exec(Scanner scanner, Channel channel);
}
