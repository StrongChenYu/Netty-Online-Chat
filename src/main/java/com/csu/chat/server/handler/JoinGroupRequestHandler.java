package com.csu.chat.server.handler;

import com.csu.chat.attribute.Attributes;
import com.csu.chat.protocol.request.JoinGroupRequestPacket;
import com.csu.chat.protocol.response.JoinGroupResponsePacket;
import com.csu.chat.session.Session;
import com.csu.chat.util.Logger;
import com.csu.chat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    private JoinGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(msg.getGroupId());

        if (channelGroup != null) {
            Session session = ctx.channel().attr(Attributes.SESSION).get();

            channelGroup.add(ctx.channel());

            responsePacket.setSuccess(true);
            responsePacket.setReason(session.getUserName() + "加入群聊成功");

            //打印日志
            Logger.printUserOperationMsg(session.getUserName(),"加入群聊[" + msg.getGroupId() + "]");
            channelGroup.writeAndFlush(responsePacket);
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("该群不存在");
            ctx.channel().writeAndFlush(responsePacket);
        }


    }
}
