package com.csu.chat.handler;

import com.csu.chat.protocol.response.MessageResponsePacket;
import com.csu.chat.util.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket packet) throws Exception {
        Logger.printServerMsg("收到服务端的回复：" + packet.getMessage());
    }
}
