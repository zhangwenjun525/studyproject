package runtime;

import com.zhangwj.project.java8.utils.MarshallingCodeCFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/4
 * Time: 15:14
 */
public class Client {

    private static class SingletonHolder {
        static final Client instance = new Client();
    }

    public static Client getInstance() {
        return SingletonHolder.instance;
    }

    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;

    private Client() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(MarshallingCodeCFactory.buildMarshallingEncoder())
                                .addLast(MarshallingCodeCFactory.buildMarshallingDecoder())
                                .addLast(new ReadTimeoutHandler(5))
                                .addLast(new ClientHandler());
                    }
                });
    }

    public void connect() {
        try {
            channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
            System.out.println("远程服务器已经连接，可以进行数据交互");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ChannelFuture getChannelFuture() {
        if (channelFuture == null) {
            this.connect();
        }

        if (!channelFuture.channel().isActive()) {
            this.connect();
        }

        return this.channelFuture;
    }

    public static void main(String[] args) throws Exception {
        final Client client = Client.getInstance();
        ChannelFuture channelFuture = client.getChannelFuture();
        for (int i = 1; i <= 3; ++i) {
            Request request = new Request();
            request.setId("" + i);
            request.setName("pro" + i);
            request.setRequestMessage("数据信息" + i);
            channelFuture.channel().writeAndFlush(request);
            TimeUnit.SECONDS.sleep(4);
        }

        channelFuture.channel().closeFuture().sync();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("进入子线程...");
                    ChannelFuture cf = client.getChannelFuture();
                    System.out.println(cf.channel().isActive());
                    System.out.println(cf.channel().isOpen());

                    //再次发送数据
                    Request request = new Request();
                    request.setId("" + 4);
                    request.setName("pro" + 4);
                    request.setRequestMessage("数据信息" + 4);
                    cf.channel().writeAndFlush(request);
                    cf.channel().closeFuture().sync();
                    System.out.println("子线程结束.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("断开连接,主线程结束..");
    }


}
