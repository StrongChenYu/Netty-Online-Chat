package com.csu.chat.util;

import java.util.UUID;

public class UserInfo {
    public final static String ID;
    public final static String USERNAME = "chenyu";
    public final static String PWD = "123456";

    static {
        ID = UUID.randomUUID().toString();
    }
}
