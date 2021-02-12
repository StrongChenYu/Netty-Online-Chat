package com.csu.chat.protocol.response;

import com.csu.chat.protocol.Command.Command;
import com.csu.chat.protocol.Packet;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {

    String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
