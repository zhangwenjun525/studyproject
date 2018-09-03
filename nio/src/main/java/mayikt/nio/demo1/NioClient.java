package mayikt.nio.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * @author zhangwj
 * @date 2018/9/3 8:44
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        System.out.println("客户端已经启动");
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        sc.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(new Date().toString().getBytes());

        buffer.flip();
        sc.write(buffer);

        buffer.clear();
        sc.close();


    }


}
