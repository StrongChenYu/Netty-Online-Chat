package com.csu.chat.protocol;

import com.csu.chat.protocol.Command.Command;
import com.csu.chat.protocol.request.*;
import com.csu.chat.protocol.response.*;
import com.csu.chat.serialize.Serializer;
import com.csu.chat.serialize.SerializerAlgorithm;
import com.csu.chat.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.csu.chat.protocol.Command.Command.*;


public class PacketCodeC implements PacketParser {

    public static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;
    public static final PacketCodeC INSTANCE = new PacketCodeC();


    static {
        packetTypeMap = new HashMap<>();
        serializerMap = new HashMap<>();

        serializerMap.put(SerializerAlgorithm.JSON, new JsonSerializer());

        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
    }

    @Override
    public void encode(ByteBuf byteBuf, Packet packet) {
        byte[] content = Serializer.DEFAULT.serialize(packet);
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(content.length);
        byteBuf.writeBytes(content);
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
        Class clazz = Command.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }
}
