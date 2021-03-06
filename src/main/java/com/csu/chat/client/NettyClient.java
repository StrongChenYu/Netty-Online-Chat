package com.csu.chat.client;

import com.csu.chat.client.handler.*;
import com.csu.chat.coder.PacketDecoder;
import com.csu.chat.coder.PacketEncoder;
import com.csu.chat.coder.Spliter;
import com.csu.chat.client.command.ConsoleCommandManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        //空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        //拆包器
                        ch.pipeline().addLast(new Spliter());
                        //解码器
                        ch.pipeline().addLast(new PacketDecoder());
                        //心跳数据包
                        ch.pipeline().addLast(new HeartBeatTimerHandler());
                        //登录
                        ch.pipeline().addLast(new LoginResponseHandler());
                        //消息
                        ch.pipeline().addLast(new MessageResponseHandler());
                        //创建群聊
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        //加入群聊
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        //列出群聊人员列表
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        //发送消息到群聊
                        ch.pipeline().addLast(new GroupMessageResponseHandler());
                        //注销
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        //退出群聊
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        //编码器
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }


    private static void startConsoleThread(Channel channel) {
        Scanner sc = new Scanner(System.in);
        ConsoleCommandManager commandManager = new ConsoleCommandManager();
        new Thread(() -> {
            while (!Thread.interrupted()) {
                commandManager.exec(sc, channel);
            }
        }).start();
    }


}
