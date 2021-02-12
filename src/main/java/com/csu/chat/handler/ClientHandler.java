package com.csu.chat.handler;

import com.csu.chat.protocol.Packet;
import com.csu.chat.protocol.PacketCodeC;
import com.csu.chat.protocol.request.LoginRequestPacket;
import com.csu.chat.protocol.response.LoginResponsePacket;
import com.csu.chat.protocol.response.MessageResponsePacket;
import com.csu.chat.util.Logger;
import com.csu.chat.util.LoginUtil;
import com.csu.chat.util.UserInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Logger.printInfo("客户端发送登录请求！");

        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId(UserInfo.ID);
        requestPacket.setPwd(UserInfo.PWD);
        requestPacket.setName(UserInfo.USERNAME);

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), requestPacket);

        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        Packet decode = PacketCodeC.INSTANCE.decode(byteBuf);

        if (decode instanceof LoginResponsePacket) {
            Logger.printInfo("收到登录返回请求!");
            LoginResponsePacket responsePacket = (LoginResponsePacket) decode;

            if (responsePacket.isSuccess()) {
                Logger.printInfo("登录成功！");
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                Logger.printInfo("登录失败！");
            }
        } else if (decode instanceof MessageResponsePacket) {
            MessageResponsePacket responsePacket = (MessageResponsePacket) decode;
            Logger.printServerMsg("收到服务端的回复");
        }
    }
}
