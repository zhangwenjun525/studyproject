package juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/14
 * Time: 23:35
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        executorService.shutdown();
    }

}
