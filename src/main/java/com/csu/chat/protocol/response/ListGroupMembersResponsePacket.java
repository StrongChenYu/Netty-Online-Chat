package com.csu.chat.protocol.response;

import com.csu.chat.protocol.Command.Command;
import com.csu.chat.protocol.Packet;
import com.csu.chat.session.Session;
import lombok.Data;

import java.util.List;

@Data
public class ListGroupMembersResponsePacket extends Packet {
    private String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
