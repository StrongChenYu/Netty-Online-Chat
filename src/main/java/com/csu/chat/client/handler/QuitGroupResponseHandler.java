package com.csu.chat.client.handler;

import com.csu.chat.protocol.response.QuitGroupResponsePacket;
import com.csu.chat.util.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
        //如果客户端存着群聊的信息，需要处理一下
        Logger.printInfo(msg.getReason());
    }
}
