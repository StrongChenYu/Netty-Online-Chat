package com.csu.chat.protocol.response;

import com.csu.chat.protocol.Command.Command;
import com.csu.chat.protocol.Packet;

public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_RESPONSE;
    }
}
