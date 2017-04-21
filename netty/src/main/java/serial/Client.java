package serial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import utils.MarshallingCodeCFactory;

import java.net.InetSocketAddress;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/4
 * Time: 12:33
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(MarshallingCodeCFactory.buildMarshallingEncoder())
                                .addLast(MarshallingCodeCFactory.buildMarshallingDecoder())
                                .addLast(new ClientHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080)).sync();

        for (int i = 0; i < 5; ++i) {
            Req req = new Req();
            req.setId("" + i);
            req.setName("pro" + i);
            req.setRequestMessage("数据信息" + i);
/*            String path = System.getProperty("user.dir") + File.separatorChar + "sources" +  File.separatorChar + "001.jpg";
            File file = new File(path);
            FileInputStream in = new FileInputStream(file);
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            req.setAttachment(GzipUtils.gzip(data));*/
            channelFuture.channel().writeAndFlush(req);
        }


        channelFuture.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}
