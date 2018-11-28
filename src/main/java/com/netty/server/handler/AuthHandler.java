package com.netty.server.handler;

import com.netty.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 权限校验handler
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    // 接受到消息后,进行权限校验.
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //判断当前链接的channel是否保存了对应的session信息.因为客户端登录成功的时候,
        // 我们会给对应的channel绑定一个session信息.
        if (SessionUtils.hasLogin(ctx.channel())){
            System.out.println("权限校验通过...移除权限校验模块.");
            ctx.pipeline().remove(this); // 权限校验通过后,动态移除权限校验模块.
        }else {
            // 权限校验没通过,一刀切,断链接
            System.out.println("权限校验不通过,关闭对应的链接.");
            ctx.channel().close();
        }
        super.channelRead(ctx, msg);
    }
}
