package com.project.socket.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author zhangwj
 * @date 2018/9/2 15:55
 */
public class TcpClient {

    public static void main(String[] args) throws IOException {

        System.out.println("tcp客户端启动");

        Socket socket = new Socket("127.0.0.1", 8080);

        OutputStream os = socket.getOutputStream();

        os.write("我是蚂蚁课堂忠实粉丝".getBytes());

        os.close();
    }

}
