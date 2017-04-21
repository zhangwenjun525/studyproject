package thread;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/24
 * Time: 16:00
 */
public class MyQueue {

    private List<Integer> list = new LinkedList<>();

    public synchronized void put(Integer num) {
        try {
            if (list.size() == 10) {
                System.out.println("put wait");
                wait();
            }
            list.add(num);
            System.out.println(Thread.currentThread().getName() + "新增一个number:[" + num + "]" + ", size = " + list.size());
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized Integer take() {
        Integer num = null;
        try {
            if (list.size() == 0) {
                System.out.println("take wait");
                wait();
            }
            num = list.get(0);
            list.remove(0);
            System.out.println(Thread.currentThread().getName() + "移除一个number:[" + num + "]" + ", size = " + list.size());
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return num;
    }


    public static void main(String[] args) {
        MyQueue queue = new MyQueue();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    queue.put(new Random().nextInt());
                }
            }
        }, "生产者").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(queue.take());
                }
            }
        }, "消费者").start();


    }
}
