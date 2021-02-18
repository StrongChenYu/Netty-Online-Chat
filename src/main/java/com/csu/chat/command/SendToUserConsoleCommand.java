package com.csu.chat.command;

import com.csu.chat.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        MessageRequestPacket requestPacket = new MessageRequestPacket();

        //读取信息
        System.out.print("请输入要发送的消息（用户ID和消息使用冒号分割）: ");
        String input = scanner.next();

        //发送给用户
        String toUserId = input.split(":")[0];
        String msg = input.split(":")[1];

        requestPacket.setToUserId(toUserId);
        requestPacket.setMessage(msg);

        channel.writeAndFlush(requestPacket);
    }

    public static void main(String[] args) {
        String input = "chen chenyu";
        System.out.println(input.split(" ")[0]);
        System.out.println(input.split(" ")[1]);
    }
}
