package com.csu.chat.protocol.request;

import com.csu.chat.protocol.Command.Command;
import com.csu.chat.protocol.Packet;

public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
