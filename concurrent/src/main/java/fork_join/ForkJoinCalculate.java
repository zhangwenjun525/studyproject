package fork_join;

import java.util.concurrent.RecursiveTask;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/13
 * Time: 16:29
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    private static final long THENHOLD = 10000L;

    public ForkJoinCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THENHOLD) {
            long sum = 0;
            for (long i = start; i <= end; ++i) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            left.fork();  //拆分子任务,同时压入线程队列;
            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
            right.fork(); //拆分子任务,同时压入线程队列;
            return left.join() + right.join();
        }
    }
}
