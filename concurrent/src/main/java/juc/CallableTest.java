package juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/14
 * Time: 17:56
 */

class ThreadDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 1000; ++i) {
            Thread.sleep(1);
            sum += i;
        }
        System.out.println("-----------------------------");
        return sum;
    }
}

public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadDemo td = new ThreadDemo();
        FutureTask<Integer> result = new FutureTask<Integer>(td);
        new Thread(result).start();
        System.out.println(result.get());
    }


}
