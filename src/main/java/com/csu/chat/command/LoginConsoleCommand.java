package com.csu.chat.command;

import com.csu.chat.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket requestPacket = new LoginRequestPacket();

        System.out.print("请输入用户名: ");
        String userName = scanner.next();

        System.out.print("请输入密码: ");
        String userPwd = scanner.next();

        requestPacket.setName(userName);
        requestPacket.setPwd(userPwd);

        channel.writeAndFlush(requestPacket);

    }

}
