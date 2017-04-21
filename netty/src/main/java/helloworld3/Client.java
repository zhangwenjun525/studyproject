package helloworld3;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/4
 * Time: 10:06
 */
public class Client {

    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //分隔符
                        ByteBuf buf = Unpooled.copiedBuffer("$".getBytes());
                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
                        //定长
                        //socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(1024));
                        socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("utf-8")));
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080)).sync();

        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("你好服务端$".getBytes("utf-8")));
        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("你好服务端$".getBytes("utf-8")));
        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("你好服务端$".getBytes("utf-8")));


        channelFuture.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}
