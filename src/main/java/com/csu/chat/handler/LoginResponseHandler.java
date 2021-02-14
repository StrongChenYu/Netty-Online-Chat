package com.csu.chat.handler;

import com.csu.chat.protocol.request.LoginRequestPacket;
import com.csu.chat.protocol.response.LoginResponsePacket;
import com.csu.chat.util.Logger;
import com.csu.chat.util.LoginUtil;
import com.csu.chat.util.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UserInfo.ID);
        loginRequestPacket.setName(UserInfo.USERNAME);
        loginRequestPacket.setPwd(UserInfo.PWD);

        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            Logger.printInfo("登录成功！");
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            Logger.printInfo("登录失败！");
        }
    }
}
