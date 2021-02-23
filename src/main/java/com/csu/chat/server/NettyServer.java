package com.csu.chat.server;

import com.csu.chat.coder.Spliter;
import com.csu.chat.server.handler.HeartBeatRequestHandler;
import com.csu.chat.server.handler.IMHandler;
import com.csu.chat.server.handler.IMIdleStateHandler;
import com.csu.chat.server.handler.PacketCodecHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup workers = new NioEventLoopGroup();

        serverBootstrap
                .group(boss, workers)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        //空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        //拆包粘包
                        ch.pipeline().addLast(new Spliter());
                        //解码
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        //心跳检测
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        //其他
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                    }
                });

        bind(serverBootstrap, 8080);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}
