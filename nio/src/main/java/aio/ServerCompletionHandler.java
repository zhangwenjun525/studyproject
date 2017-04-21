package aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/3
 * Time: 17:01
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {

    @Override
    public void completed(AsynchronousSocketChannel asc, Server attachment) {
        //当有下一个客户端接入时，直接调用Server的accept方法，这样反复下去，保证多个客户端可以阻塞
        attachment.getAssc().accept(attachment, this);
        read(asc);
    }

    @Override
    public void failed(Throwable exc, Server attachment) {
        exc.printStackTrace();
    }


    private void read(AsynchronousSocketChannel asc) {
        //读取数据
        ByteBuffer buf = ByteBuffer.allocate(1024);
        asc.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer resultSize, ByteBuffer attachment) {
                //1.复位
                attachment.flip();

                //2.获取读取的字节数
                System.out.println("Server -> 收到客户端的数据长度为: " + resultSize);

                //3.获取读取的数据
                String resultData = new String(attachment.array()).trim();
                System.out.println("Server -> 收到客户端的数据为: " + resultData);
                String response = "服务器响应，收到客户端数据: " + resultData;
                write(asc, response);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }

    private void write(AsynchronousSocketChannel asc, String response) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(response.getBytes());
            buffer.flip();
            asc.write(buffer).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
