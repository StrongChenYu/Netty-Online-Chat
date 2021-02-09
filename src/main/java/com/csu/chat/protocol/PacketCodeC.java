package com.csu.chat.protocol;


import com.csu.chat.protocol.request.LoginRequestPacket;
import com.csu.chat.protocol.response.LoginResponsePacket;
import com.csu.chat.serialize.Serializer;
import com.csu.chat.serialize.SerializerAlgorithm;
import com.csu.chat.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.csu.chat.protocol.Command.Command.LOGIN_REQUEST;
import static com.csu.chat.protocol.Command.Command.LOGIN_RESPONSE;


public class PacketCodeC implements PacketParser {

    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;
    public static final PacketCodeC INSTANCE = new PacketCodeC();


    static {
        packetTypeMap = new HashMap<>();
        serializerMap = new HashMap<>();

        serializerMap.put(SerializerAlgorithm.JSON, new JsonSerializer());
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
    }

    @Override
    public ByteBuf encode(ByteBufAllocator allocator, Packet packet) {
        ByteBuf byteBuf = allocator.DEFAULT.ioBuffer();

        byte[] content = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(content.length);
        byteBuf.writeBytes(content);

        return byteBuf;
    }

    @Override
    public Packet decode(ByteBuf byteBuf) {

        //跳过magic number
        byteBuf.skipBytes(4);

        //跳过version
        byteBuf.skipBytes(1);

        //跳过序列化算法
        byte serializerAlgorithm = byteBuf.readByte();

        //读取控制的命令
        byte command = byteBuf.readByte();

        //读取长度
        byte[] content = new byte[byteBuf.readInt()];
        byteBuf.readBytes(content);

        Serializer serializer = getSerializer(serializerAlgorithm);
        Class<? extends Packet> packetType = getPacketType(command);

        if (serializer != null && packetType != null) {
            return serializer.deserializer(packetType, content);
        }

        return null;
    }

    private Class<? extends Packet> getPacketType(Byte type) {
        return packetTypeMap.get(type);
    }

    private Serializer getSerializer(Byte type) {
        return serializerMap.get(type);
    }

    public static void main(String[] args) {
        System.out.println("chenyu");
    }
}
