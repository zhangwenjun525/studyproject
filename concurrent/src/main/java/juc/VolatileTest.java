package juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/14
 * Time: 10:11
 */
class Counter {
    public static volatile int count = 0;

    public static void inc() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("counter: " + count++);
    }
}

class CounterByVolatile {
    public static volatile AtomicInteger count = new AtomicInteger(0);


    public static void inc() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("counterByVolatile: " + count.getAndIncrement());
    }
}


public class VolatileTest {

    public static void main(String[] args) {
/*
        for(int i = 0; i < 10000; ++i){
            new Thread(()->Counter.inc()).start();
        }
*/


        for (int i = 0; i < 10000; ++i) {
            new Thread(() -> CounterByVolatile.inc()).start();
        }
    }


}
