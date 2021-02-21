package com.csu.chat.server.handler;

import com.csu.chat.protocol.request.CreateGroupRequestPacket;
import com.csu.chat.protocol.response.CreateGroupResponsePacket;
import com.csu.chat.util.IdUtil;
import com.csu.chat.util.Logger;
import com.csu.chat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket requestPacket) throws Exception {
        List<String> userList = requestPacket.getUserList();
        List<String> userNameList = new ArrayList<>();

        ChannelGroup channels = new DefaultChannelGroup(ctx.executor());

        for (String userId : userList) {
            Channel channel = SessionUtil.getChannelById(userId);
            if (channel != null) {
                channels.add(channel);
                userNameList.add(SessionUtil.getUserName(userId));
            }
        }

        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        String groupId = IdUtil.getRandomGroupId();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        responsePacket.setUserNameList(userNameList);
        SessionUtil.bindChannelGroup(groupId, channels);

        Logger.printInfo("创建群聊" + userNameList + "成功！群聊ID为: " + groupId);
        channels.writeAndFlush(responsePacket);
    }


}
