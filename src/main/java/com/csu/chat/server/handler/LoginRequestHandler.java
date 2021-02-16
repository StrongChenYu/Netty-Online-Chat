package com.csu.chat.server.handler;

import com.csu.chat.protocol.request.LoginRequestPacket;
import com.csu.chat.protocol.response.LoginResponsePacket;
import com.csu.chat.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket responsePacket = login(ctx, msg);
        ctx.channel().writeAndFlush(responsePacket);
    }

    private LoginResponsePacket login(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) {
        LoginResponsePacket responsePacket = new LoginResponsePacket();

        if (valid(requestPacket)) {
            //登录成功
            responsePacket.setSuccess(true);
            responsePacket.setReason("登录成功！");
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            //登录失败
            responsePacket.setSuccess(false);
            responsePacket.setReason("登录失败！");
        }

        return responsePacket;
    }

    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }
}
