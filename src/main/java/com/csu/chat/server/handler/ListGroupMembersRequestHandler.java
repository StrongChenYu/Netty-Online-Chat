package com.csu.chat.server.handler;

import com.csu.chat.attribute.Attributes;
import com.csu.chat.protocol.request.ListGroupMembersRequestPacket;
import com.csu.chat.protocol.response.ListGroupMembersResponsePacket;
import com.csu.chat.session.Session;
import com.csu.chat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        List<Session> sessionList = new ArrayList<>();

        String groupId = msg.getGroupId();

        responsePacket.setGroupId(groupId);
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        for (Channel channel : channelGroup) {
            Session session = channel.attr(Attributes.SESSION).get();
            sessionList.add(session);
        }
        responsePacket.setSessionList(sessionList);

        ctx.channel().writeAndFlush(responsePacket);
    }
}
