package com.csu.chat.client.command;

import com.csu.chat.protocol.request.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LogoutConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket requestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(requestPacket);
    }
}
