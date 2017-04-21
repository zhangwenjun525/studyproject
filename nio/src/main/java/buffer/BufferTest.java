package buffer;

import java.nio.IntBuffer;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/3
 * Time: 12:49
 */
public class BufferTest {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        buffer.put(13);
        buffer.put(21);
        buffer.put(35);

        System.out.println("容量: " + buffer.capacity());
        System.out.println("限制: " + buffer.limit());

        buffer.flip();
        System.out.println("使用flip复位: " + buffer);

        System.out.println("获取下标为1的元素: " + buffer.get(1));
        System.out.println("get(index)方法，position位置不变: " + buffer.position());
        buffer.put(1, 4);
        System.out.println("put(index, value)方法，position位置不变: " + buffer.position());

        for (int i = 0; i < buffer.limit(); ++i) {
            System.out.print(buffer.get() + "\t");
        }
        System.out.println("\n遍历之后:" + buffer);
    }

}
