package com.myth.netty02.client;


import com.myth.netty02.handler.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 最简配的Netty客户端的配置.
 */
public class NettyClient02 {
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
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });
        // 客户端启动进行链接.
        // 添加一个Listener监听器,用于异步的监听链接结果.
        bootstrap.connect(HOST,PORT).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()){
                    System.out.println("客户端链接服务器["+HOST+":"+PORT+"]成功");
                }else {
                    System.out.println("客户端链接服务器["+HOST+":"+PORT+"]失败");
                }

            }
        });
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
