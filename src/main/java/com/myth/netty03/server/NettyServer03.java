package com.myth.netty03.server;

import com.myth.netty02.handler.FirstServerHandler;
import com.myth.netty03.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 最简启动配置
 *
 * 客户端和
 */
public class NettyServer03 {
    private  static final  int PORT = 8000;

    public static void main(String[] args) {
        // 1.构建服务器的启动引导类.
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        // 创建线程组.
        serverBootstrap.group(boss,work)
        // 指定IO线程模型
        .channel(NioServerSocketChannel.class)
        // 添加IO处理器 , 处理链接过来的channel.所以需要创建childHandler
                // 注意这里使用的泛型是  NioSocketChannel 因为是child处理,实际需要核客户端保持一致.
        .childHandler(new ChannelInitializer<NioSocketChannel>() {
            // 复写initChannel方法,只要有新链接链接到了服务器,就会调用此方法.
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                System.out.println("接受到客户端链接请求....");
                // 添加Handler进行处理.
                ch.pipeline().addLast(new ServerHandler());
            }
        });
        // 绑定端口
        serverBootstrap.bind(PORT);
    }


    /**
     * 提供一种让服务器动态绑定端口的重试机制.
     * @param serverBootstrap
     * @param port
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
