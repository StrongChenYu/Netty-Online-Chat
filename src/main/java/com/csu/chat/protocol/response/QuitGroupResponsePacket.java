package com.csu.chat.protocol.response;

import com.csu.chat.protocol.Command.Command;
import com.csu.chat.protocol.Packet;
import lombok.Data;

@Data
public class QuitGroupResponsePacket extends Packet {
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
