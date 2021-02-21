package com.csu.chat.client.handler;

import com.csu.chat.protocol.response.ListGroupMembersResponsePacket;
import com.csu.chat.session.Session;
import com.csu.chat.util.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg) throws Exception {
        Logger.printInfo("群成员列表如下");
        for (Session session : msg.getSessionList()) {
            System.out.println(session.getUserId() + ":" + session.getUserName());
        }
    }
}
