package com.csu.chat.server.handler;

import com.csu.chat.attribute.Attributes;
import com.csu.chat.protocol.request.QuitGroupRequestPacket;
import com.csu.chat.protocol.response.QuitGroupResponsePacket;
import com.csu.chat.util.Logger;
import com.csu.chat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    private QuitGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();

        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        if (channelGroup == null) {
            responsePacket.setReason("该群聊不存在");
            responsePacket.setSuccess(false);
        } else {
            boolean remove = channelGroup.remove(ctx.channel());
            if (remove) {
                responsePacket.setSuccess(true);
                responsePacket.setReason("群聊退出成功！");
                Logger.printUserOperationMsg(ctx.channel().attr(Attributes.SESSION).get().getUserName(), "退出群聊[" + groupId +"]");
            } else {
                responsePacket.setSuccess(false);
                responsePacket.setReason("群聊退出失败！");
            }
        }

        ctx.channel().writeAndFlush(responsePacket);
    }
}
