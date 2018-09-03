package com.project.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangwj
 * @date 2018/9/2 15:51
 */
public class TcpServer {

    public static void main(String[] args) throws IOException {

        System.out.println("tcp服务器端启动");

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        ServerSocket ss = new ServerSocket(8080);

        try {
            while (true) {
                Socket socket = ss.accept();

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        InputStream is = null;
                        try {
                            is = socket.getInputStream();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        byte[] bytes = new byte[1024];

                        int len = 0;
                        try {
                            len = is.read(bytes);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String result = new String(bytes, 0, len);

                        System.out.println("服务器接受客户端数据: " + result);

                    }
                });
            }
        } catch (Exception e) {

        } finally {
            ss.close();
            executorService.shutdown();
        }


    }
}
