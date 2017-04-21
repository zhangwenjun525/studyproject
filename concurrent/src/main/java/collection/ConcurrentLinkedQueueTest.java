package collection;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/27
 * Time: 10:12
 */
public class ConcurrentLinkedQueueTest {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.stream().forEach(System.out::println);
        System.out.println("-----------------------------");
        Integer poll = queue.poll();
        System.out.println(poll);
        System.out.println("-----------------------------");
        queue.stream().forEach(System.out::println);
    }
}
