package com.csu.chat.server.handler;

import com.csu.chat.protocol.request.HeartBeatRequestPacket;
import com.csu.chat.protocol.response.HeartBeatResponsePacket;
import com.csu.chat.util.Logger;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    public static HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        Logger.printInfo("服务端收到心跳");
        ctx.channel().writeAndFlush(new HeartBeatResponsePacket());
    }
}
