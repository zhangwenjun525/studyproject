package base;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/3
 * Time: 11:00
 */
public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        OutputStream out = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            out = socket.getOutputStream();
            out.write("客户端数据".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            socket = null;
        }
    }

}
