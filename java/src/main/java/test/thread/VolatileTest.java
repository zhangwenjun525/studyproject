package test.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/7/7
 * Time: 16:19
 */
public class VolatileTest {

    static volatile AtomicInteger at = new AtomicInteger(0);

    public static class PlusTask implements Runnable{

        @Override
        public void run() {
           for(int k = 0; k < 10000; ++k){
               at.incrementAndGet();
           }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for(int i = 0; i < 10; ++i){
            threads[i] = new Thread(new PlusTask());
            threads[i].start();
        }

        for(int i = 0; i < 10; ++i){
            threads[i].join();
        }

        System.out.println(at.get());
    }


}
