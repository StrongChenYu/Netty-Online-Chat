package com.csu.chat.server.handler;

import com.csu.chat.protocol.request.MessageRequestPacket;
import com.csu.chat.protocol.response.MessageResponsePacket;
import com.csu.chat.session.Session;
import com.csu.chat.util.Logger;
import com.csu.chat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        Session session = SessionUtil.getSessionByChannel(ctx.channel());

        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setMessage(msg.getMessage());
        responsePacket.setFromUserId(session.getUserId());
        responsePacket.setFromUserName(session.getUserName());

        //拿到要发送到的userId
        Channel channel = SessionUtil.getChannelById(msg.getToUserId());

        if (SessionUtil.hasLogin(channel)) {
            Logger.printClientMsg(session.getUserName(), SessionUtil.getUserName(msg.getToUserId()), msg.getMessage());
            channel.writeAndFlush(responsePacket);
        } else {
            //这里对方客户端不在线
            //需要将消息存起来或者一些其他的操作
            Logger.printInfo("客户端[" + msg.getToUserId() + "]不在线");
        }
    }
}
