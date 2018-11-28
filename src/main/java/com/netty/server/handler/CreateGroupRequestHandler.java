package com.netty.server.handler;

import com.netty.protocol.request.CreateGroupRequestPacket;
import com.netty.protocol.response.CreateGroupResponsePacket;
import com.netty.utils.IDUtils;
import com.netty.utils.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        //1.获得建群请求中的所有userid.
        List<String> userIdList = createGroupRequestPacket.getUserIdList();
        // 这个是为了返回.
        List<String> userNameList = new ArrayList<>();
        //2.创建一个Channel组.注意参数
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        // 3.筛选出所有待加入群聊的用户的channel
        for (String userId: userIdList){
            Channel channel = SessionUtils.getChannel(userId);
            if (channel != null){ // 说明当前用户在线.
                channelGroup.add(channel);
                userNameList.add(SessionUtils.getSeesion(channel).getUserName());
            }
        }
        // 4.创建响应信息
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(IDUtils.getUUID());
        createGroupResponsePacket.setUserNameList(userNameList);
        // 5. 给每个客户端发送拉群通知
        // 注意发送方式
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());


    }
}
