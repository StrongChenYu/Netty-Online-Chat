package com.csu.chat.util;

import java.util.UUID;

public class IdUtil {
    public static String getRandomId() {
        String id = UUID.randomUUID().toString();
        return id.split("-")[0];
    }

    public static String getRandomGroupId() {
        String id = UUID.randomUUID().toString();
        return id.split("-")[1];
    }
}
