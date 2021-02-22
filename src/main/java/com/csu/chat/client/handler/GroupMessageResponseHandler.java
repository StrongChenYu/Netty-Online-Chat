package com.csu.chat.client.handler;

import com.csu.chat.protocol.response.GroupMessageResponsePacket;
import com.csu.chat.util.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) throws Exception {
        Logger.printInfo("[" + msg.getFromGroupId() + "]" + msg.getFromUserName() + ":" + msg.getMessage());
    }
}
