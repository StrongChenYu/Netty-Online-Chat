package com.csu.chat.client.command;

import com.csu.chat.protocol.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class JoinGroupCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入群聊ID: ");
        String groupId = scanner.next();

        JoinGroupRequestPacket requestPacket = new JoinGroupRequestPacket();
        requestPacket.setGroupId(groupId);

        channel.writeAndFlush(requestPacket);
    }
}
