package com.csu.chat.protocol.response;

import com.csu.chat.protocol.Command.Command;
import com.csu.chat.protocol.Packet;
import lombok.Data;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;
    private String message;

    private String fromUserName;
    private String fromUserId;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
