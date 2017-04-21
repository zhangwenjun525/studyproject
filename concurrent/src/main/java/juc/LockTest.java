package juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/14
 * Time: 18:06
 */

class Ticket implements Runnable {

    private volatile int ticket = 100;
    private Lock lock = new ReentrantLock();


    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                if (ticket > 0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " -------> " + ticket--);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}

public class LockTest {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        for (int i = 0; i < 10; ++i) {
            new Thread(ticket).start();
        }
    }

}
