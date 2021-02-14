package com.csu.chat.protocol;

import io.netty.buffer.ByteBuf;

/**
 * 解析Packet的接口
 */
public interface PacketParser {

    /**
     * 将packet
     * @param packet
     * @return
     */
    void encode(ByteBuf byteBuf, Packet packet);

    /**
     * 将buteBuf中的内容提取出来，然后解析成Packet
     * @param byteBuf
     * @return
     */
    Packet decode(ByteBuf byteBuf);


}
