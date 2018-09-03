package mayikt.nio.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author zhangwj
 * @date 2018/9/3 8:48
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        System.out.println("服务器端已经启动");

        //1.创建服务器通道
        ServerSocketChannel ssc = ServerSocketChannel.open();

        //2.设置为非阻塞模式
        ssc.configureBlocking(false);

        //3.绑定端口号
        ssc.bind(new InetSocketAddress(8080));

        //4.获取选择器
        Selector selector = Selector.open();

        //5.将通道注册到选择器中，并且监听就绪事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        //6.轮询获取就绪事件
        while (selector.select() > 0) {

            //7.获取当前选择器上注册的事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()) {

                //8.获取就绪事件
                SelectionKey sk = it.next();

                //9.判断事件是否就绪
                if (sk.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    SocketChannel sc = (SocketChannel) sk.channel();

                    int len = 0;
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    while ((len = sc.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }
                }

                it.remove();
            }

        }


    }
}
