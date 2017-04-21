package utils;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量使用示例
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/29
 * Time: 15:58
 */
public class UseSemaphare {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < 50; ++i) {
            final int NO = i;
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("accessing " + NO);
                    TimeUnit.SECONDS.sleep(new Random().nextInt(10) + 1);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }

}
