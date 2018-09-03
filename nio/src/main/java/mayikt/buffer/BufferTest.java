package mayikt.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author zhangwj
 * @date 2018/9/2 16:33
 */
public class BufferTest {


    /**
     * position<br> 缓冲区正在操作的位置 默认从0开始。
     * limit<br>  界面(缓冲区可用大小)
     * capacity;<br> 缓冲区最大容量，一旦声明不能改变
     * <p>
     * 核心方法:
     * put() 往buff存放数据
     * get() 获取数据
     */
    @Test
    public void test01() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println("------------------------------------");
        System.out.println("往byteBuffer中存储数据....");
        byteBuffer.put("abcd1".getBytes());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println("----------读取值...----------");
        //开启读取模式
        byteBuffer.flip();
        System.out.println("position:" + byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes, 0, bytes.length));

        System.out.println("----------重复读取----------");
        byteBuffer.rewind();//重复读取
        System.out.println("position:" + byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        byte[] bytes2 = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes2);
        System.out.println(new String(bytes2, 0, bytes2.length));
        //清空缓冲数据被遗忘值...
        System.out.println("----------清空缓冲区--------------");
        byteBuffer.clear();
        System.out.println("position:" + byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println((char) byteBuffer.get());
    }

    @Test
    public void test02() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        String str = "abcd";
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes, 0, 2);
        byteBuffer.mark();
        System.out.println(new String(bytes, 0, 2));
        System.out.println(byteBuffer.position());
        System.out.println("-------------------------------------------------");
        byteBuffer.get(bytes, 2, 2);
        System.out.println(new String(bytes, 2, 2));
        byteBuffer.reset();
        System.out.println(byteBuffer.position());
    }

}
