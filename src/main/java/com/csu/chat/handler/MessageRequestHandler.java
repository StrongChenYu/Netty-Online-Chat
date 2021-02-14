package com.csu.chat.handler;

import com.csu.chat.protocol.request.MessageRequestPacket;
import com.csu.chat.protocol.response.MessageResponsePacket;
import com.csu.chat.util.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        MessageResponsePacket responsePacket = replyMessage(msg);
        ctx.channel().writeAndFlush(responsePacket);
    }

    private MessageResponsePacket replyMessage(MessageRequestPacket requestPacket) {
        MessageResponsePacket responsePacket = new MessageResponsePacket();

        Logger.printClientMsg(requestPacket.getMessage());
        responsePacket.setMessage("收到消息！");

        return responsePacket;
    }
}
