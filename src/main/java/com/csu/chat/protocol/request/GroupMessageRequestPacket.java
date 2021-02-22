package com.csu.chat.protocol.request;

import com.csu.chat.protocol.Command.Command;
import com.csu.chat.protocol.Packet;
import lombok.Data;

@Data
public class GroupMessageRequestPacket extends Packet {

    private String toGroupId;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
