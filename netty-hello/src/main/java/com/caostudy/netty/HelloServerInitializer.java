package com.caostudy.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author Cao Study
 * @description <h1>初始化器，channel注册后，会执行里面的相应初始化方法</h1>
 * @date 2021-10-09 19:57
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 1.通过SocketChannel去获得对应的管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 2.通过管道，添加handler
        // 2.1HttpServerCodec是由netty自己提供的助手类，可以理解为拦截器,由HttpRequestDecoder, HttpResponseEncoder组成
        // 当请求到服务端，我们需要做编解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        // 2.2添加自定义的助手类，返回“hello netty~”
        pipeline.addLast("customHandler", new CustomHandler());
    }
}
