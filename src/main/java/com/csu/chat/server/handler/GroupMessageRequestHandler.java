package com.csu.chat.server.handler;

import com.csu.chat.attribute.Attributes;
import com.csu.chat.protocol.request.GroupMessageRequestPacket;
import com.csu.chat.protocol.response.GroupMessageResponsePacket;
import com.csu.chat.session.Session;
import com.csu.chat.util.Logger;
import com.csu.chat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        String toGroupId = msg.getToGroupId();
        String message = msg.getMessage();

        Session fromSession = ctx.channel().attr(Attributes.SESSION).get();

        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        responsePacket.setMessage(message);
        responsePacket.setFromGroupId(toGroupId);
        responsePacket.setFromUserId(fromSession.getUserId());
        responsePacket.setFromUserName(fromSession.getUserName());

        ChannelGroup channels = SessionUtil.getChannelGroup(toGroupId);

        Logger.printUserOperationMsg(fromSession.getUserName(), "发送消息[" + message +"]到群聊[" + toGroupId +"]");
        channels.writeAndFlush(responsePacket);
    }
}
