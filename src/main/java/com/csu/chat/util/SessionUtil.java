package com.csu.chat.util;

import com.csu.chat.attribute.Attributes;
import com.csu.chat.session.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private static final Map<String, String> userIdToNameMap = new ConcurrentHashMap<>();

    public static void addUser(String userId, String userName) {
        userIdToNameMap.put(userId, userName);
    }

    public static String getUserName(String userId) {
        return userIdToNameMap.get(userId);
    }

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        addUser(session.getUserId(), session.getUserName());
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unbindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSessionByChannel(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        if (channel == null) return false;
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSessionByChannel(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannelById(String userId) {
        return userIdChannelMap.get(userId);
    }

}
