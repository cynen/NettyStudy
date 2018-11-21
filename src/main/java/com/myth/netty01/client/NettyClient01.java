package com.myth.netty01.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 最简配的Netty客户端的配置.
 */
public class NettyClient01 {
    private static  final  int PORT = 8000;
    private static  final  String HOST = "127.0.0.1";

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
                        System.out.println("client....");
                    }
                });
        bootstrap.connect(HOST,PORT);
    }
}
