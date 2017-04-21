package aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/3
 * Time: 16:47
 */
public class Server {

    //线程池
    private ExecutorService executorService;

    //线程组
    private AsynchronousChannelGroup threadGroup;

    //服务器通道
    private AsynchronousServerSocketChannel assc;

    public AsynchronousServerSocketChannel getAssc() {
        return assc;
    }

    public Server(int port) {
        try {
            //创建一个线程池
            executorService = Executors.newCachedThreadPool();

            //创建线程组
            threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);

            //创建服务器通道
            assc = AsynchronousServerSocketChannel.open(threadGroup);

            //进行绑定
            assc.bind(new InetSocketAddress(port));

            System.out.println("server start, port: " + port);

            assc.accept(this, new ServerCompletionHandler());

            TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8080);
    }

}
