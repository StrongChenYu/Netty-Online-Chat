package com.csu.chat.client.handler;

import com.csu.chat.protocol.response.LogoutResponsePacket;
import com.csu.chat.util.Logger;
import com.csu.chat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            Logger.printInfo("注销成功！");
            SessionUtil.unbindSession(ctx.channel());
        } else {
            Logger.printInfo("注销失败！");
        }
    }
}
