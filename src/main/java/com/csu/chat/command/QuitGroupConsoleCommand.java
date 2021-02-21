package com.csu.chat.command;

import com.csu.chat.protocol.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequestPacket requestPacket = new QuitGroupRequestPacket();

        System.out.print("请输入群聊ID: ");
        String groupId = scanner.next();

        requestPacket.setGroupId(groupId);

        channel.writeAndFlush(requestPacket);
    }
}
