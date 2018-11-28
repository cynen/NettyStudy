package com.netty.protocol.response;

import com.netty.protocol.Packet;
import com.netty.protocol.command.Command;

import java.util.List;

public class CreateGroupResponsePacket extends Packet {
    private boolean success; // 建群是否成功

    private String groupId; // 群号

    private List<String> userNameList;// 群内所有用户.

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getUserNameList() {
        return userNameList;
    }

    public void setUserNameList(List<String> userNameList) {
        this.userNameList = userNameList;
    }
}
