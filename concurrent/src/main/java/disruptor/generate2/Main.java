package disruptor.generate2;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.zhangwj.project.java8.thread.disruptor.generate1.Trade;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/2
 * Time: 23:35
 */
public class Main {

    public static void main(String[] args) {
        Instant start = Instant.now();
        int bufferSize = 1024;
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        Disruptor<Trade> disruptor = new Disruptor<Trade>(() -> new Trade(), bufferSize, executorService, ProducerType.SINGLE, new BusySpinWaitStrategy());

        /**
         * 菱形操作
         * 1.使用disruptor创建消费者组C1,C2
         */
/*        EventHandlerGroup<Trade> handlerGroup = disruptor.handleEventsWith(new Handler1(), new Handler2());
        handlerGroup.then(new Handler3());*/

        /**
         * 顺序操作
         */
        //EventHandlerGroup<Trade> handlerGroup = disruptor.handleEventsWith(new Handler1()).handleEventsWith(new Handler2()).handleEventsWith(new Handler3());

        /**
         * 六边形操作
         */
        Handler1 handler1 = new Handler1();
        Handler2 handler2 = new Handler2();
        Handler3 handler3 = new Handler3();
        Handler4 handler4 = new Handler4();
        Handler5 handler5 = new Handler5();
        disruptor.handleEventsWith(handler1, handler2);
        disruptor.after(handler1).handleEventsWith(handler4);
        disruptor.after(handler2).handleEventsWith(handler5);
        disruptor.after(handler4, handler5).handleEventsWith(handler3);


        disruptor.start();
        CountDownLatch latch = new CountDownLatch(1);
        executorService.execute(new TradePublisher(latch, disruptor));

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        disruptor.shutdown();
        executorService.shutdown();
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }
}
