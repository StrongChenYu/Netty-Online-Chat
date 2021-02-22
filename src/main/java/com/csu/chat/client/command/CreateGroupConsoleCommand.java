package com.csu.chat.client.command;

import com.csu.chat.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Collections;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {

        System.out.print("请输入创建群聊的用户列表: (用逗号分割): ");
        CreateGroupRequestPacket requestPacket = new CreateGroupRequestPacket();

        String input = scanner.next();
        String[] userList = input.split(",");

        Collections.addAll(requestPacket.getUserList(),userList);

        channel.writeAndFlush(requestPacket);
    }
}
