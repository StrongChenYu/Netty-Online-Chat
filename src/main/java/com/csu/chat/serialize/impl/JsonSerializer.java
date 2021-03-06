package com.csu.chat.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.csu.chat.serialize.Serializer;
import com.csu.chat.serialize.SerializerAlgorithm;

public class JsonSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserializer(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
