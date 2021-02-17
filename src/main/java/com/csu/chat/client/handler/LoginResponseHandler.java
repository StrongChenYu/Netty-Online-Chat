package com.csu.chat.client.handler;

import com.csu.chat.protocol.response.LoginResponsePacket;
import com.csu.chat.session.Session;
import com.csu.chat.util.Logger;
import com.csu.chat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            Logger.printInfo("登录成功！");
            Logger.printInfo("我的ID是：" + msg.getUserId());

            Session session = new Session();
            session.setUserId(msg.getUserId());
            session.setUserName(msg.getUserName());

            SessionUtil.bindSession(session, ctx.channel());
//            System.out.println(ctx.channel());
        } else {
            Logger.printInfo("登录失败！");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭！");
    }
}
