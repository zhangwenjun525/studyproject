package juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/14
 * Time: 21:42
 */
class Clerk {

    private volatile int product = 0;
    private Lock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();

    public void get() {
        lock.lock();
        try {
            Thread.sleep(10);
            if (product >= 100) {
                System.out.println("产品已满");
                try {
                    full.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " ------> " + ++product);
            empty.signal();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }

    public void sale() {
        try {
            lock.lock();
            Thread.sleep(1);
            if (product <= 0) {
                System.out.println("缺货");
                try {
                    empty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " ------> " + --product);
            full.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}

class Productor implements Runnable {

    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true) {
            clerk.get();
        }
    }
}

class Consumer implements Runnable {

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true) {
            clerk.sale();
        }
    }
}


public class ProductorAndConsuerTest {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        new Thread(new Productor(clerk), "生产者").start();
        new Thread(new Consumer(clerk), "消费者").start();
    }

}
