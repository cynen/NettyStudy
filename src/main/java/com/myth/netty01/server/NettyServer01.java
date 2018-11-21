package com.myth.netty01.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 最简启动配置
 */
public class NettyServer01 {
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
                // 添加Handler进行处理.
                System.out.println("接受到客户端链接请求....");
            }
        });
        // 绑定端口
        serverBootstrap.bind(PORT);
    }

}
