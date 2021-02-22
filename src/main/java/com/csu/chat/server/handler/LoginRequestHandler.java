package com.csu.chat.server.handler;

import com.csu.chat.protocol.request.LoginRequestPacket;
import com.csu.chat.protocol.response.LoginResponsePacket;
import com.csu.chat.session.Session;
import com.csu.chat.util.IdUtil;
import com.csu.chat.util.Logger;
import com.csu.chat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private LoginRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket responsePacket = login(ctx, msg);
        ctx.channel().writeAndFlush(responsePacket);
    }

    private LoginResponsePacket login(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) {
        LoginResponsePacket responsePacket = new LoginResponsePacket();

        if (valid(requestPacket)) {
            String userId = IdUtil.getRandomId();

            //登录成功
            responsePacket.setSuccess(true);
            responsePacket.setReason("登录成功！");
            responsePacket.setUserId(userId);
            responsePacket.setUserName(requestPacket.getName());

            //保存登录的状态
            Session session = new Session();
            session.setUserId(userId);
            session.setUserName(requestPacket.getName());

            SessionUtil.bindSession(session, ctx.channel());
            Logger.printInfo("["+ requestPacket.getName() +"]" + "登录成功");
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

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unbindSession(ctx.channel());
    }
}
