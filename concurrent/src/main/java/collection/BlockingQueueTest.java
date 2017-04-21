package collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/27
 * Time: 10:21
 */
public class BlockingQueueTest {
    public static void testBlockingQueue(BlockingQueue<Integer> queue) {
        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    queue.put(++i);
                    System.out.println(Thread.currentThread().getName() + "当前长度" + queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                try {
                    Integer take = queue.take();
                    System.out.println(Thread.currentThread().getName() + "获取" + take);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }

    public static void main(String[] args) throws InterruptedException {
        //testBlockingQueue(new ArrayBlockingQueue<>(5));
        testBlockingQueue(new LinkedBlockingQueue<>(5));
    }
}
