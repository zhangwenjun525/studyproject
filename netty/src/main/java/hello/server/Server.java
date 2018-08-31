package hello.server;

import helloworld.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    public static void main(String[] args) throws InterruptedException {
        //1.第一个线程组用于接收client端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        //2.第二个线程组用于实际的业务处理操作
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            //3.创建一个辅助类Bootstrap，就是对Server进行一系列的配置
            ServerBootstrap bootstrap = new ServerBootstrap();

            //将两个工作线程组加入
            bootstrap.group(bossGroup, workGroup)
                    //制定使用NioServerSocketChannel这种类型的通道
                    .channel(NioServerSocketChannel.class)
                    //使用childHandler 去绑定具体的 事件处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    })
                    /**
                     * 服务器端TCP内核维护了两个队列，我们称为A,B
                     * 客户端向服务器端connect的时候，会发送带有SYN标志的包(第一次握手)
                     * 服务器收到客户端发送的SYN时,向客户端发送SYN_ACK确认(第二次握手)
                     * 此时TCP内核模块把客户端连接加入到A队列中，然后服务器收到客户端发送的ACK时(第三次握手)
                     * TCP内核模块把客户端连接从A队列移到B队列，连接完成，应用程序accept会返回
                     * 也就是说accept从B队列中取出三次握手的连接
                     * A队列和B队列的长度之和是backlog，当A，B队列的长度之和大于backlog时，新连接会被TCP内核拒绝
                     * 所以backlog过小，可能会出现accept的速度跟不上，A,B队列满了，会导致新的客户端无法连接
                     * 要注意的是：backlog对程序支持的连接数没有影响，backlog影响的只是没有被accept取出的连接。
                     */
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //保存连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //使用绑定指定的端口进行监听
            ChannelFuture future = bootstrap.bind(8080).sync();
        }finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }



    }

}
