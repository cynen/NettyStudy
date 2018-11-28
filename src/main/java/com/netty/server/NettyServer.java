package com.netty.server;

import com.netty.codec.PacketDecoder;
import com.netty.codec.PacketEncoder;
import com.netty.codec.Spliter;
import com.netty.server.handler.AuthHandler;
import com.netty.server.handler.CreateGroupRequestHandler;
import com.netty.server.handler.MessageRequestHandler;
import com.netty.server.handler.ServerLoginHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
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
public class NettyServer {
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
                ch.pipeline().addLast(new Spliter());
                ch.pipeline().addLast(new PacketDecoder());
                ch.pipeline().addLast(new ServerLoginHandler()); //接受登录请求.
                ch.pipeline().addLast(new AuthHandler());// 鉴权
                ch.pipeline().addLast(new MessageRequestHandler()); // 处理客户端消息.
                ch.pipeline().addLast(new CreateGroupRequestHandler());
                ch.pipeline().addLast(new PacketEncoder());
            }
        }).handler(new ChannelInitializer<NioServerSocketChannel>() {
            @Override
            protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                System.out.println("启动服务器...");
            }
        });
        serverBootstrap.option(ChannelOption.SO_BACKLOG,1024) // 设置服务端最大连接数1024
                .childOption(ChannelOption.SO_KEEPALIVE,true) // 设置和客户端的链接属性,长连接
                .childOption(ChannelOption.TCP_NODELAY,true); // 设置TCP属性.
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
