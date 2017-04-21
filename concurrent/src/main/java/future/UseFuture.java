package future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/29
 * Time: 15:06
 */
public class UseFuture implements Callable<String> {

    private String param;

    public UseFuture(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        Random random = new Random();
        TimeUnit.SECONDS.sleep(random.nextInt(5) + 1);
        return this.param.concat("处理完成");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String queryStr = "query";
        FutureTask<String> task1 = new FutureTask<>(new UseFuture(queryStr));
        FutureTask<String> task2 = new FutureTask<>(new UseFuture(queryStr));

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future = executor.submit(task1);
        Future<?> future1 = executor.submit(task2);

        try {
            System.out.println("处理实际的业务逻辑");
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("数据1: " + task1.get());
        System.out.println("数据2: " + task2.get());

        executor.shutdown();
    }
}
