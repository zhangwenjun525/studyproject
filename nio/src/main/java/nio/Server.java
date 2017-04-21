package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/3
 * Time: 13:26
 */
public class Server implements Runnable {

    private Selector selector;

    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public Server(int port) {
        try {
            //1.打开多路复用器
            this.selector = Selector.open();

            //2.打开服务器通道
            ServerSocketChannel ssc = ServerSocketChannel.open();

            //3.设置服务器通道为非阻塞模式
            ssc.configureBlocking(false);

            //4.绑定端口
            ssc.bind(new InetSocketAddress(port));


            //5.将服务器通道注册到多路复用器上，并且监听阻塞事件
            ssc.register(this.selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                //1.让多路复用器开始监听
                selector.select();

                //2.返回多路复用器已经选择的结果集
                Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();

                //3.进行遍历
                while (iterator.hasNext()) {
                    //4.获取一个选择的元素
                    SelectionKey key = iterator.next();

                    //5.将该元素从集合中移除
                    iterator.remove();

                    //6.判断是否有效
                    if (key.isValid()) {
                        //7.如果是阻塞状态
                        if (key.isAcceptable()) {
                            accept(key);
                        }

                        //8.如果是可读状态
                        if (key.isReadable()) {
                            read(key);
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read(SelectionKey key) {
        try {
            //1.清空缓冲区旧的数据
            this.buffer.clear();

            //2.获取之前注册的socket通道对象
            SocketChannel sc = (SocketChannel) key.channel();

            //3.读取数据
            int count = sc.read(this.buffer);

            //4.如果没有数据
            if (-1 == count) {
                sc.close();
                key.cancel();
                return;
            }

            //5.有数据则进行读取，读取之前需要进行复位操作
            this.buffer.flip();

            //6.根据缓冲区中数据长度创建相应大小的byte数组
            byte[] bytes = new byte[this.buffer.remaining()];

            //7.接受缓冲区数据
            this.buffer.get(bytes);

            //8.打印结果
            String body = new String(bytes).trim();
            System.out.println("server: " + body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void accept(SelectionKey key) {
        try {
            //1.获取服务器通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();

            //2.执行阻塞方法
            SocketChannel socketChannel = ssc.accept();

            //3.设置阻塞模式
            socketChannel.configureBlocking(false);

            //4.注册到多用复用器，并设置读取标识
            socketChannel.register(this.selector, SelectionKey.OP_READ);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Thread(new Server(8085)).start();
    }

}
