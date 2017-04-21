package juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/15
 * Time: 10:37
 */
class Seller {

    private Integer count = 0;
    private final static Integer MAX_COUNT = 100;
    private Lock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();

    public void stock() {
        try {
            lock.lock();
            if (count == MAX_COUNT) {
                full.await();
            }
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() + " --------> " + ++count);
            empty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void sell() {
        try {
            lock.lock();
            if (count == 0) {
                empty.await();
            }
            Thread.sleep(200);
            System.out.println(Thread.currentThread().getName() + " --------> " + count--);
            full.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class ProductorConsumerTest {

    public static void main(String[] args) {

        Seller seller = new Seller();

        new Thread(() -> {
            while (true) {
                seller.stock();
            }
        }, "生产者").start();

        new Thread(() -> {
            while (true) {
                seller.sell();
            }
        }, "消费者").start();
    }

}
