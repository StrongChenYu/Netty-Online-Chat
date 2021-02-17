package com.csu.chat.protocol.request;

import com.csu.chat.protocol.Command.Command;
import com.csu.chat.protocol.Packet;
import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {

    String toUserId;
    String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    public MessageRequestPacket() {}

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
