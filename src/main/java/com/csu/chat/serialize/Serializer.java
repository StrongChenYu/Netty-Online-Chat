package com.csu.chat.serialize;


import com.csu.chat.serialize.impl.JsonSerializer;

public interface Serializer {

    /**
     * 默认的序列化对象
     */
    Serializer DEFAULT = new JsonSerializer();

    /**
     *返回序列化算法
     * @return
     */
    byte getSerializerAlgorithm();


    /**
     * 序列化方法
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化方法
     *将二进制转化为T对象
     * T是泛型
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserializer(Class<T> clazz, byte[] bytes);
}
