package juc;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/14
 * Time: 17:38
 */
class LatchDemo implements Runnable {

    private CountDownLatch countDownLatch;

    public LatchDemo(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0; i < 50000; ++i) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}

public class CountDownLatchTest {

    public static void main(String[] args) {
        final int count = 3;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        LatchDemo ld = new LatchDemo(countDownLatch);

        Instant start = Instant.now();
        for (int i = 0; i < count; ++i) {
            new Thread(ld).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Instant end = Instant.now();

        System.out.println(Duration.between(start, end).toMillis());
    }

}
