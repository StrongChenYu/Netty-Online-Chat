package com.csu.chat.util;

import java.util.Date;

public class Logger {

    public static void printInfo(String msg) {
        System.out.println(new Date() + " " + msg);
    }
    public static void printClientMsg(String msg) {
        System.out.println(new Date() + " " + "[收到客户端发来的消息]" + msg);
    }

    public static void printClientMsg(String userName, String msg) {
        System.out.println(new Date() + " " + "[ " + userName + "]" + msg);
    }

    public static void printServerMsg(String msg) {
        System.out.println(new Date() + " " + "[收到服务端发来的消息]" + msg);
    }


}
