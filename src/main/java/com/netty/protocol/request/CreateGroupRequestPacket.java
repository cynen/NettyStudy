package com.netty.protocol.request;

import com.netty.protocol.Packet;
import com.netty.protocol.command.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * 键群请求值需要提供所有的userId列表
 */
public class CreateGroupRequestPacket extends Packet {
    List<String> userIdList = new ArrayList<>();
    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
