package com.project.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author zhangwj
 * @date 2018/8/31 17:30
 */
public class UdpServer {

    public static void main(String[] args) throws IOException {
        System.out.println("udp服务器启动");
        DatagramSocket ds = new DatagramSocket(8080);
        byte[] bytes = new byte[1024];
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);

        ds.receive(dp);

        System.out.println("请求ip:" + dp.getAddress() + ", 端口:" + dp.getPort());

        String result = new String(dp.getData(), 0, dp.getLength());
        System.out.println(result);

        ds.close();
    }
}
