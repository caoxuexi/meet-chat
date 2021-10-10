package com.caostudy.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Cao Study
 * @description <h1>HelloServer 客户端发送请求，服务器返回hello</h1>
 * @date 2021-10-09 19:41
 */
public class HelloServer {

    public static void main(String[] args) throws InterruptedException {
        //1.定义一对线程组
        //1.1 主线程组,用于接收客户端的连接，不做任何处理
        EventLoopGroup fatherGroup=new NioEventLoopGroup();
        //1.2 从线程组，father线程组会把任务丢给worker线程组去完成任务
        EventLoopGroup workerGroup=new NioEventLoopGroup();

        try {
            //2.netty服务器的创建，ServerBootstrap是一个启动类
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(fatherGroup,workerGroup) //设置主从线程组
                    .channel(NioServerSocketChannel.class) //设置nio的双向通道
                    .childHandler(new HelloServerInitializer()); //子处理器，用于处理workerGroup
            //3.同步的方式启动server，并设置8088为启动的端口
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            //4.同步的方式监听关闭的channel
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //5.优雅关闭线程组
            fatherGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
