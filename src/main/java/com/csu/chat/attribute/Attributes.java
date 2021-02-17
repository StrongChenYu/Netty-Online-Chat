package com.csu.chat.attribute;

import com.csu.chat.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes{

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
