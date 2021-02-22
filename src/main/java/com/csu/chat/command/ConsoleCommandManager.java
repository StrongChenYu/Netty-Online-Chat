package com.csu.chat.command;

import com.csu.chat.util.Logger;
import com.csu.chat.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand{
    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("login", new LoginConsoleCommand());
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupCommand());
        consoleCommandMap.put("listGroupMembers", new ListGroupMembersConsoleCommand());
        consoleCommandMap.put("quitGroup", new QuitGroupConsoleCommand());
        consoleCommandMap.put("sendToGroup", new GroupMessageConsoleCommand());
    }


    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("请输入指令：");
        String command = scanner.next();
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if (consoleCommand == null) {
            System.out.println("指令无法识别");
            return;
        }

        if (!command.equals("login")) {
            if (!SessionUtil.hasLogin(channel)) {
                Logger.printInfo("请先登录！");
                return;
            }
        }

        consoleCommand.exec(scanner, channel);
        waitForResponse();
    }

    private static void waitForResponse() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException ignored) {

        }
    }
}
