package com.csu.chat.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] bytes = "你好我是陈宇哈哈哈哈哈哈哈哈哈".getBytes(StandardCharsets.UTF_8);

        for (int i = 0; i < 100; i++) {
            ByteBuf byteBuf = ctx.alloc().buffer();
            byteBuf.writeBytes(bytes);
            ctx.channel().writeAndFlush(byteBuf);
        }
    }

}
