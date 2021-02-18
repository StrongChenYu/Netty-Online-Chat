package com.csu.chat.protocol.request;

import com.csu.chat.protocol.Command.Command;
import com.csu.chat.protocol.Packet;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userList;

    public CreateGroupRequestPacket() {
        userList = new ArrayList<>();
    }

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
