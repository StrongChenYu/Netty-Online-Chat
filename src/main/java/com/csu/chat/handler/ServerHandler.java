package com.csu.chat.handler;

import com.csu.chat.protocol.request.LoginRequestPacket;
import com.csu.chat.protocol.response.LoginResponsePacket;
import com.csu.chat.protocol.Packet;
import com.csu.chat.protocol.PacketCodeC;
import com.csu.chat.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket) {
            Logger.printInfo("收到客户端登录请求！");
            LoginRequestPacket requestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket responsePacket = new LoginResponsePacket();

            if (valid(requestPacket)) {
                responsePacket.setSuccess(true);
                Logger.printInfo("用户验证成功！");
            } else {
                responsePacket.setSuccess(false);
                responsePacket.setReason(new Date() + " " + "用户验证失败！");
            }
            Logger.printInfo("返回用户信息！");
//            ctx.writeAndFlush(PacketCodeC.INSTANCE.encode(responsePacket));
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }
}
