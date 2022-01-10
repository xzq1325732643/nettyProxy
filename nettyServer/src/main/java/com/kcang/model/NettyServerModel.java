package com.kcang.model;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class NettyServerModel {

    private final Logger myLogger = LoggerFactory.getLogger(NettyServerModel.class);

    private int bindPort;
    public NettyServerModel(int port){
        bindPort = port;
    }
    public NettyServerModel(){
        bindPort = 19191;
    }

    public void run(){
        myLogger.info("正在启动NettyServer: "+bindPort);
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(new InetSocketAddress("0.0.0.0",bindPort));

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast();//加载Inbound
                    socketChannel.pipeline().addFirst();//加载outbound
                    //socketChannel.pipeline().addLast(serverHandler);
                }
            });
        }catch (Exception e){

        }
    }
}