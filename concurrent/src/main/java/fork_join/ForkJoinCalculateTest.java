package fork_join;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/13
 * Time: 17:13
 */
public class ForkJoinCalculateTest {

    @Test
    public void test1() {
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(1, 100000000L);
        Long invoke = pool.invoke(task);
        System.out.println(invoke);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }

    @Test
    public void test02() {
        Instant start = Instant.now();
        long reduce = LongStream.range(0, 100000001L).parallel().reduce(0, Long::sum);
        System.out.println(reduce);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }

}
