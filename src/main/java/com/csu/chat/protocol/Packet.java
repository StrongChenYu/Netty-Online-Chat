package com.csu.chat.protocol;


import lombok.Data;

@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    public abstract Byte getCommand();
}
