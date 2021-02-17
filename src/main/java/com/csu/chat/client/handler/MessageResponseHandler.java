package com.csu.chat.client.handler;

import com.csu.chat.protocol.response.MessageResponsePacket;
import com.csu.chat.util.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket packet) throws Exception {
        Logger.printClientMsg(packet.getFromUserName(), packet.getMessage());
    }
}
