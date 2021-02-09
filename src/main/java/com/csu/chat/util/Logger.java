package com.csu.chat.util;

import java.util.Date;

public class Logger {

    public static void printInfo(String msg) {
        System.out.println(new Date() + " " + msg);
    }
}
