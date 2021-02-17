package com.csu.chat.util;

import java.util.UUID;

public class UserInfo {
    public static String getRandomId() {
        String id = UUID.randomUUID().toString();
        return id.split("-")[0];
    }
}
