package utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/29
 * Time: 9:29
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(2);

        new Thread(() -> {
            System.out.println("t1开始执行");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1结束执行");
        }, "t1").start();


        new Thread(() -> {
            System.out.println("t2开始执行");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2结束执行");
            latch.countDown();
        }, "t2").start();

        new Thread(() -> {
            System.out.println("t3开始执行");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t3结束执行");
            latch.countDown();
        }, "t3").start();
    }
}
