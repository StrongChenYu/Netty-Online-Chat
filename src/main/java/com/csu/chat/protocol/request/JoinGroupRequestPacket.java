package com.csu.chat.protocol.request;

import com.csu.chat.protocol.Command.Command;
import com.csu.chat.protocol.Packet;
import lombok.Data;

@Data
public class JoinGroupRequestPacket extends Packet {
    private String groupId;
    
    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
