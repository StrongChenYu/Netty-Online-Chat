package com.csu.chat.client.handler;

import com.csu.chat.protocol.response.JoinGroupResponsePacket;
import com.csu.chat.util.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            Logger.printInfo(msg.getReason());
        } else {
            Logger.printInfo(msg.getReason());
        }
    }
}
