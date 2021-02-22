package com.csu.chat.client.command;

import com.csu.chat.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class GroupMessageConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("请输入群号和消息（以冒号相隔）:");
        String input = scanner.next();

        String[] split = input.split(":");

        String groupId = split[0];
        String message = split[1];

        GroupMessageRequestPacket requestPacket = new GroupMessageRequestPacket();
        requestPacket.setToGroupId(groupId);
        requestPacket.setMessage(message);

        channel.writeAndFlush(requestPacket);
    }
}
