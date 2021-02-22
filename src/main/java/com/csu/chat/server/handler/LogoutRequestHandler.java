package com.csu.chat.server.handler;

import com.csu.chat.protocol.request.LogoutRequestPacket;
import com.csu.chat.protocol.response.LogoutResponsePacket;
import com.csu.chat.session.Session;
import com.csu.chat.util.Logger;
import com.csu.chat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        Session session = SessionUtil.getSessionByChannel(ctx.channel());
        SessionUtil.unbindSession(ctx.channel());

        LogoutResponsePacket responsePacket = new LogoutResponsePacket();
        responsePacket.setSuccess(true);

        Logger.printInfo("[" + session.getUserName() + "]已注销");
        ctx.channel().writeAndFlush(responsePacket);
    }
}
