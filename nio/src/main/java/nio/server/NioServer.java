package nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {

    private Selector selector;

    public void initServer(int port) throws IOException{
        //获得一个ServerSocket通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //设置通道为非阻塞
        serverSocketChannel.configureBlocking(false);

        //将通道对应的serverSocket绑定到port端口
        serverSocketChannel.socket().bind(new InetSocketAddress(port));

        //获取一个通道管理器
        this.selector = Selector.open();

        //将通道管理器和通道绑定
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException{
        System.out.println("服务端启动成功");
        while (true){
            selector.select();
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                handler(key);
            }
        }
    }

    public void handler(SelectionKey key) throws IOException {
        if(key.isAcceptable()){
            handlerAccept(key);
        }else if(key.isReadable()){
            handlerRead(key);
        }
    }

    public void handlerAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        System.out.println("新的客户端连接");
        socketChannel.register(this.selector, SelectionKey.OP_READ);
    }

    public void handlerRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = socketChannel.read(buffer);
        if(read > 0){
            byte[] data = buffer.array();
            String message = new String(data).trim();
            System.out.println("服务端收到消息: " + message);

            //回写数据
            ByteBuffer outBuffer = ByteBuffer.wrap("好的".getBytes());
            socketChannel.write(outBuffer);
        }else {
            System.out.println("客户端关闭");
            key.cancel();
        }
    }

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer();
        server.initServer(8000);
        server.listen();
    }




}

