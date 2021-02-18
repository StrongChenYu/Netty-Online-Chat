package com.csu.chat.client.handler;

import com.csu.chat.protocol.response.CreateGroupResponsePacket;
import com.csu.chat.util.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        Logger.printInfo("创建群聊" + msg.getUserNameList() + "成功！");
    }
}
