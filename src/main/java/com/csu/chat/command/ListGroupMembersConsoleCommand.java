package com.csu.chat.command;

import com.csu.chat.protocol.request.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("请输入群聊ID: ");
        String groupId = scanner.next();

        ListGroupMembersRequestPacket requestPacket = new ListGroupMembersRequestPacket();
        requestPacket.setGroupId(groupId);

        channel.writeAndFlush(requestPacket);
    }
}
