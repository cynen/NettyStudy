package com.netty.client;


import com.netty.client.console.ConsoleCommand;
import com.netty.client.console.ConsoleCommandManager;
import com.netty.client.console.LoginConsoleCommand;
import com.netty.client.handler.ClientLoginHandler;
import com.netty.client.handler.CreateGroupResponsehandler;
import com.netty.client.handler.MessageResponseHandler;
import com.netty.codec.PacketDecoder;
import com.netty.codec.PacketEncoder;
import com.netty.codec.Spliter;
import com.netty.protocol.request.CreateGroupRequestPacket;
import com.netty.protocol.request.LoginRequestPacket;
import com.netty.protocol.request.MessageRequestPacket;
import com.netty.utils.SessionUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 最简配的Netty客户端的配置.
 */
public class NettyClient {
    private static  final  int PORT = 8000;
    private static  final  String HOST = "127.0.0.1";
    private  static final int MAX_RETRY = 5;
    public static void main(String[] args) {
        // 创建客户端的启动引导类.
        Bootstrap bootstrap = new Bootstrap();
        // 创建线程组
        NioEventLoopGroup work = new NioEventLoopGroup();
        // 启动类配置
        // 添加线程组
        bootstrap.group(work)
                // 配置IO模型为NIO
                .channel(NioSocketChannel.class)
                // 添加handler,注意区分服务端.
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 添加客户端的pipeline
                        // ChannelHandler
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new ClientLoginHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponsehandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000);// 超时.

        // 客户端启动进行链接.
        // 添加一个Listener监听器,用于异步的监听链接结果.
        bootstrap.connect(HOST,PORT).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()){
                    System.out.println("客户端链接服务器["+HOST+":"+PORT+"]成功，启动控制台线程……");
                    // 获得链接后的channel对象.
                    Channel channel = ((ChannelFuture) future).channel();
                    startConsole(channel);
                }else {
                    System.out.println("客户端链接服务器["+HOST+":"+PORT+"]失败");
                }

            }
        });
    }

    private  static void startConsole(Channel channel){
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner sc = new Scanner(System.in);

        // 启动控制台,输入数据.
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!Thread.interrupted()){
                if (!SessionUtils.hasLogin(channel)){
                    loginConsoleCommand.exec(sc,channel);
                }else {
                    // 登录才能操作...
                    // 初略的处理.用户登录完成之后,
                    // 输入任意指令, 后面拼接上所有用户id,即可建群.
                    consoleCommandManager.exec(sc,channel);
                }
            }
        }).start();

    }

    /**
     * 提供一个可以带重连机制的重试机制.
     * @param bootstrap
     * @param host
     * @param port
     * @param retry
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(()->connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
